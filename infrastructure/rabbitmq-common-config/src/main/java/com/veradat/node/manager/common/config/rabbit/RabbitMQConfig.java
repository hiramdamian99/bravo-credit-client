/*
 * D. R. © Veradat Smart Network, S.A.P.I de C.V., Ciudad de México, 2023
 * VERADAT PROPRIETARY/CONFIDENCIAL. Use is subject to license terms.
 *
 * Project: veradat-node-manager
 * Module: rabbitmq-common-config
 * File: RabbitMQConfig.java
 */

/**
 *
 */
package com.veradat.node.manager.common.config.rabbit;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author veradat
 *
 */
@Configuration
@ConditionalOnProperty(name="veradat.broker-messages.messageBroker", havingValue="rabbitmq", matchIfMissing=true)
public class RabbitMQConfig {

    @Bean
    MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
