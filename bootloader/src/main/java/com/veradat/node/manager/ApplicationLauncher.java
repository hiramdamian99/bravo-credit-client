/*
 * D. R. © Veradat Smart Network, S.A.P.I de C.V., Ciudad de México, 2023
 * VERADAT PROPRIETARY/CONFIDENCIAL. Use is subject to license terms.
 *
 * Project: veradat-node-manager
 * Module: bootloader
 * File: ApplicationLauncher.java
 */

package com.veradat.node.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * Clase principal que lanza la aplicación.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableAutoConfiguration(exclude= {RabbitAutoConfiguration.class} )
@ComponentScan(basePackages = {"com.veradat"})
public class ApplicationLauncher {

    /**
     * Método principal que inicia la aplicación.
     *
     * @param args Los argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        SpringApplication.run(ApplicationLauncher.class, args);
    }
}
