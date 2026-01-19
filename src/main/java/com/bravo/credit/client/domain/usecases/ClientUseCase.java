/*
 * D. R. © Veradat Smart Network, S.A.P.I de C.V., Ciudad de México, 2023
 * VERADAT PROPRIETARY/CONFIDENCIAL. Use is subject to license terms.
 *
 * Project: veradat-node-manager
 * File: NodeMappingUseCase.java
 */

package com.bravo.credit.client.domain.usecases;


import com.bravo.credit.client.domain.inputport.ClientAsyncInputPort;
import com.bravo.credit.client.domain.model.Client;
import com.bravo.credit.client.domain.outputport.PersistencePort;


import com.bravo.credit.client.domain.usecases.helpers.MxValidations;
import com.bravo.credit.client.infrastructure.security.SecurityAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public ClientUseCase(SecurityAdapter securityAdapter, MxValidations mxValidations, PersistencePort persistencePort)
    {
        this.securityAdapter = securityAdapter;
        this.mxValidations = mxValidations;
        this.persistencePort = persistencePort;
    }

    /**
     * Return new node mapping.
     *
     * @param enqueryNodeId the origin node
     * @return the enquery node Id
     */

    public List<Client> getByDestinyMapping(Client client)  {
        return persistencePort.getByDestinyMapping(client);
    }



    @Override
    public void createdClient(Client client) throws Exception {
        switch (client.getCountry()) {
            case "MX":
                mxValidations.validateCurp(client.getIdentifier());
                break;
            case "US":
                break;
            default:
                throw new Exception("Pais no valido para crear cliente: " + client.getCountry());
        }



        persistencePort.createdClient( client);
    }



    /**
     * Register node mapping list.
     *
     * @param nodeMappings the origin node ids
     *
     */

    @Override
    public void persistNodeMappings(Client client) throws Exception {

    }


}
