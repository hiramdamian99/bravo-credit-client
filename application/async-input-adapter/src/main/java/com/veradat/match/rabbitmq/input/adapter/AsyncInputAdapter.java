package com.veradat.match.rabbitmq.input.adapter;

import com.veradat.commons.exception.VeradatException;
import com.veradat.commons.exception.utils.IdentifierManager;
import com.veradat.lib.cache.model.QueueEntity;
import com.veradat.lib.messages.jms.cfg.JMSErrorHandler;
import com.veradat.lib.messages.jms.cfg.JMSQueueSender;
import com.veradat.lib.messages.jms.cfg.JMSRetryConfig;
import com.veradat.lib.messages.jms.cfg.general.JMSGeneralConsumerAdapter;
import com.veradat.lib.security.annotations.VeradatAuthority;
import com.veradat.nodemanager.domain.inputport.NodeMappingAsyncInputPort;
import com.veradat.nodemanager.domain.model.match.NodeMappings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "veradat.broker-messages.messageBroker", havingValue = "jms", matchIfMissing = false)
public class AsyncInputAdapter extends JMSGeneralConsumerAdapter {

    static {
        IdentifierManager.registerClassIdentifier(AsyncInputAdapter.class,"AIA");
    }

    private final NodeMappingAsyncInputPort nodeMappingUseCase;

    private final Logger logger = LoggerFactory.getLogger(AsyncInputAdapter.class);

    @Autowired
    public AsyncInputAdapter(
        NodeMappingAsyncInputPort nodeMappingUseCase,
        JMSQueueSender jmsQueueSender,
        JMSErrorHandler jmsErrorHandler
    ) {
        super(jmsErrorHandler);
        logger.info("AsyncInputAdapter created");
        this.jmsQueueSender = jmsQueueSender;
        this.nodeMappingUseCase = nodeMappingUseCase;
    }

    /**
     * Persiste la informacion de un proceso de cotejo programado
     *
     * @param queueEntity
     */
    @Retryable(
        retryFor = {VeradatException.class},
        maxAttemptsExpression = "${veradat.broker-messages.jms.maxAttempts}",
        backoff = @Backoff(
            delayExpression = "${veradat.broker-messages.jms.backoffDelay}",
            multiplierExpression = "${veradat.broker-messages.jms.backoffMultiplier}"
        ),
        exceptionExpression = JMSRetryConfig.RETRY_EXPRESSION
    )
    @JmsListener(
        destination = "${veradat.input.queues.persist-node-routings}",
        containerFactory = "jmsListenerContainerFactory"
    )
    @VeradatAuthority(hasPermissions = "PE")
    public void persistScheduledMatchProcess(QueueEntity<NodeMappings> queueEntity) {
        nodeMappingUseCase.persistNodeMappings(queueEntity.getBody().getMappings());
    }
}
