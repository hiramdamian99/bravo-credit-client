/*
 * D. R. © Hiram Solutions de C.V., Ciudad de México, 2026
 * CONFIDENTIAL Use is subject to license terms.
 *
 * Project: bravo-credit-client
 * File: ClientUseCase.java
 */
package com.bravo.credit.client.domain.usecases;


import com.bravo.credit.client.domain.inputport.ClientAsyncInputPort;
import com.bravo.credit.client.domain.model.BankInformation;
import com.bravo.credit.client.domain.model.Client;
import com.bravo.credit.client.domain.model.ClientRequest;
import com.bravo.credit.client.domain.model.ClientResponse;
import com.bravo.credit.client.domain.model.InformationResponse;
import com.bravo.credit.client.domain.outputport.BankPort;
import com.bravo.credit.client.domain.outputport.PersistencePort;


import com.bravo.credit.client.domain.usecases.helpers.CoValidations;
import com.bravo.credit.client.domain.usecases.helpers.MxValidations;
import com.bravo.credit.client.infrastructure.security.SecurityAdapter;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

/**
 * Use case for node mappings
 * @Author: Hiram Lopez Damian
 * @LastContributor: Hiram Lopez Damian
 * @Created At: 05/03/2025
 * @Updated At: 13/01/2026
 */
@Service
public class ClientUseCase implements ClientAsyncInputPort {


    private final Logger logger = LoggerFactory.getLogger(ClientUseCase.class);


    private final SecurityAdapter securityAdapter;


    private final MxValidations mxValidations;

    private final CoValidations coValidations;
    
    private final BankPort bankPort;

    /**
     * logger
     */


    private final PersistencePort persistencePort;

    /**
     * Instantiates a new Node mapping use case.
     *
     * @param persistencePort the persistence port
     */
    @Autowired
    public ClientUseCase(SecurityAdapter securityAdapter, MxValidations mxValidations, CoValidations coValidations, BankPort bankPort, PersistencePort persistencePort)
    {
        this.securityAdapter = securityAdapter;
        this.mxValidations = mxValidations;
        this.coValidations = coValidations;
        this.bankPort = bankPort;
        this.persistencePort = persistencePort;
    }

    /**
     * Return new node mapping.
     *
     * @param client the origin node
     * @return the enquery node Id
     */





    /**
     * Validaciones por tipo de origen
     * @param client cliente a dar de alta
     */
    @Override
    public InformationResponse createdClient(Client client) throws Exception {

        logger.info("Comienzan las validaciones del cliente : ",  client.getIdentifier());

        BankInformation bankInformation = null;

        switch (client.getCountry()) {
            case "Mexico":
                mxValidations.validateCurp(client.getIdentifier());
                mxValidations.validateAmountMonthIncomeMexico(client.getMonthlyIncome(), client.getAmount());
                bankInformation = bankPort.bbvaInformation(client);
                break;
            case "Colombia":
                coValidations.validateCc(client.getIdentifier());
                coValidations.validateAmountMonthIncomeColombia(client.getMonthlyIncome(), client.getAmount());
                bankInformation = bankPort.bancolombiaInformation(client);
                break;
            default:
                throw new Exception("Pais no valido para crear cliente: " + client.getCountry());

        }
        persistencePort.createdClient( client);

        InformationResponse informationResponse = new InformationResponse();
        informationResponse.setMessage("Cliente creado exitosamente");
        informationResponse.setAccountId(bankInformation.getAccountId());
        informationResponse.setApproved(bankInformation.getApproved());
        informationResponse.setStatusRequest("APR");
        informationResponse.setCreatedAt(Instant.now());
        return informationResponse;
    }


    /**
     * Validaciones por tipo de origen
     * @param client cliente a dar de alta
     */
    public void bankValidation(Client client) throws Exception {
        persistencePort.createdClient( client);
    }




    /**
     * Register node mapping list.
     *
     * @param client the origin node ids
     *
     */
    @Override
    public List<ClientResponse> getClientData(ClientRequest client) throws Exception {

    return persistencePort.getClientData(client);

    }


}
