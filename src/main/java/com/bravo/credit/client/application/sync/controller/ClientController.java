/*
 * D. R. © Veradat Smart Network, S.A.P.I de C.V., Ciudad de México, 2023
 * VERADAT PROPRIETARY/CONFIDENCIAL. Use is subject to license terms.
 *
 * Project: veradat-node-manager
 * Module: adapter-rest
 * File: NodeManagerController.java
 */

package com.bravo.credit.client.application.sync.controller;


import com.bravo.credit.client.application.sync.api.ClientApi;
import com.bravo.credit.client.domain.inputport.ClientAsyncInputPort;
import com.bravo.credit.client.domain.model.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;



/**
 *  Node manager controller.
 * @Author: Hiram Lopez Damian
 * @LastContributor: Hiram Lopez Damian
 * @Created At: 05/03/2025
 * @Updated At: 13/01/2026
 */
@RestController
public class ClientController implements ClientApi {



    private final Logger logger = LoggerFactory.getLogger(ClientController.class);
    private final ClientAsyncInputPort nodeMappingAsyncInputPort;

    @Autowired
    public ClientController(ClientAsyncInputPort nodeMappingAsyncInputPort) {
        this.nodeMappingAsyncInputPort = nodeMappingAsyncInputPort;
    }



    /**
     * This method is used to get a process
     * @param enqueryNodeId the enquery node id
     * @return Mapping
     */
    @Override
    public ResponseEntity<Client> getProcess(HttpHeaders header,
                                              String enqueryNodeId)  {

        Client nodeMapping = nodeMappingAsyncInputPort.getByDestinyMapping(enqueryNodeId);
        if (nodeMapping == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(nodeMapping, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<Void> createdClient(HttpHeaders header, Client createdClient) throws Exception {

         nodeMappingAsyncInputPort.createdClient(createdClient);

        return new ResponseEntity<>( HttpStatus.OK);
    }



}
