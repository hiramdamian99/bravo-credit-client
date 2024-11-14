package com.veradat.vdt.node.manager.application.adapter;

import com.veradat.commons.exception.utils.IdentifierManager;
import com.veradat.lib.cache.model.QueueEntity;
import com.veradat.lib.security.annotations.VeradatAuthority;
import com.veradat.vdt.node.manager.domain.inputport.NodeMappingAsyncInputPort;
import com.veradat.vdt.node.manager.domain.model.match.NodeMappings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(
        name = "veradat.broker-messages.messageBroker",
        havingValue = "jms",
        matchIfMissing = false)
public class AsyncInputAdapter {

    static {
        IdentifierManager.registerClassIdentifier(AsyncInputAdapter.class, "AIA");
    }

    private final NodeMappingAsyncInputPort nodeMappingUseCase;

    private final Logger logger = LoggerFactory.getLogger(AsyncInputAdapter.class);

    @Autowired
    public AsyncInputAdapter(NodeMappingAsyncInputPort nodeMappingUseCase) {
        this.nodeMappingUseCase = nodeMappingUseCase;
    }

    /**
     * Persiste la informacion de un proceso de cotejo programado
     *
     * @param queueEntity
     */
    @JmsListener(
            destination = "${veradat.input.queues.persist-node-routings}",
            containerFactory = "jmsListenerContainerFactory")
    @VeradatAuthority(hasPermissions = "PE")
    public void persistScheduledMatchProcess(QueueEntity<NodeMappings> queueEntity) {
        nodeMappingUseCase.persistNodeMappings(queueEntity.getBody().getMappings());
    }
}
