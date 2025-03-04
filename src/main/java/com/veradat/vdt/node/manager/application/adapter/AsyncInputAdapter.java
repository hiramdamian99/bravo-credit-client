package com.veradat.vdt.node.manager.application.adapter;

import com.veradat.commons.exception.VeradatException;
import com.veradat.commons.exception.utils.IdentifierManager;
import com.veradat.lib.messages.general.annotation.VeradatListener;
import com.veradat.vdt.node.manager.domain.inputport.NodeMappingAsyncInputPort;
import com.veradat.vdt.node.manager.domain.model.match.NodeMappings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AsyncInputAdapter {

    static {
        IdentifierManager.registerClassIdentifier(AsyncInputAdapter.class, "AIA");
    }

    private final NodeMappingAsyncInputPort nodeMappingUseCase;


    @Autowired
    public AsyncInputAdapter(NodeMappingAsyncInputPort nodeMappingUseCase) {
        this.nodeMappingUseCase = nodeMappingUseCase;
    }

    /**
     * Persiste la informacion de un proceso de cotejo programado
     * @param nodeMappings
     */
    @VeradatListener(
        queues = "${veradat.input.queues.persist-node-routings}",
        permission=  "PE"
    )
    public void persistScheduledMatchProcess(NodeMappings nodeMappings) throws VeradatException {
        nodeMappingUseCase.persistNodeMappings(nodeMappings.getMappings());
    }
}
