/*
 * D. R. © Hiram Solutions de C.V., Ciudad de México, 2026
 * CONFIDENTIAL Use is subject to license terms.
 *
 * Project: bravo-credit-client
 * File: AsyncInputAdapter.java
 */


package com.bravo.credit.client.application.async;


import com.bravo.credit.client.domain.inputport.ClientAsyncInputPort;
import com.bravo.credit.client.domain.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * This class persist node routings information
 */
@Service
public class AsyncInputAdapter {


    private final ClientAsyncInputPort nodeMappingUseCase;


    @Autowired
    public AsyncInputAdapter(ClientAsyncInputPort nodeMappingUseCase) {
        this.nodeMappingUseCase = nodeMappingUseCase;
    }

    /**
     * Persiste la informacion de un proceso de cotejo programado
     * @param client
     */
    public void persistScheduledMatchProcess(Client client) throws Exception {
    }
}
