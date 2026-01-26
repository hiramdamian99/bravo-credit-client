package com.bravo.credit.client.application.async.BrokerConfig;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarable;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import org.springframework.amqp.core.*;


@Configuration
@EnableConfigurationProperties(BrokerProperties.class)
public class RabbitInfraConfig {

    @Bean
    public Declarables rabbitDeclarables(BrokerProperties props) {
        List<Declarable> declarables = new ArrayList<>();

        props.getBindings().forEach((alias, cfg) -> {
            DirectExchange exchange = new DirectExchange(cfg.getExchange(), true, false);
            Queue queue = QueueBuilder.durable(cfg.getQueue()).build();
            Binding binding = BindingBuilder.bind(queue).to(exchange).with(cfg.getRoutingKey());

            declarables.add(exchange);
            declarables.add(queue);
            declarables.add(binding);
        });

        return new Declarables(declarables);
    }
}