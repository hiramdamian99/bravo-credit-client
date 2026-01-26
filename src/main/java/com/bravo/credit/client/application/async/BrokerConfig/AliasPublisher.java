package com.bravo.credit.client.application.async.BrokerConfig;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class AliasPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final BrokerProperties props;

    public AliasPublisher(RabbitTemplate rabbitTemplate, BrokerProperties props) {
        this.rabbitTemplate = rabbitTemplate;
        this.props = props;
    }

    public void publish(String alias, Object payload) {
        var cfg = props.mustGet(alias);
        rabbitTemplate.convertAndSend(cfg.getExchange(), cfg.getRoutingKey(), payload);
    }
}