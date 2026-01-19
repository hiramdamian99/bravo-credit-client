/*
 * D. R. © Veradat Smart Network, S.A.P.I de C.V., Ciudad de México, 2023
 *  VERADAT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * Project: veradat-node-manager
 * File: AsyncInputAdapter
 * Author: Hiram Lopez Damian
 * Created At: 12/09/2023
 * Updated At: 12/03/2025
 * Description: This class persist node routings information
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
        nodeMappingUseCase.persistNodeMappings(client);
    }
}
