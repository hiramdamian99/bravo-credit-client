/*
 * D. R. © Veradat Smart Network, S.A.P.I de C.V., Ciudad de México, 2023
 * VERADAT PROPRIETARY/CONFIDENCIAL. Use is subject to license terms.
 *
 * Project: veradat-node-manager
 * Module: bootloader
 * File: ApplicationLauncher.java
 */

package com.bravo.credit.client.bootloader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Clase principal que lanza la aplicación.
 */
@EntityScan(basePackages = {"com.bravo"})
@EnableJpaRepositories(basePackages = {"com.bravo"})
@SpringBootApplication(
        scanBasePackages = {"com.bravo"})
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
