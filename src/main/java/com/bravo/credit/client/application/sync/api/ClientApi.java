/*
 * D. R. © Veradat Smart Network, S.A.P.I de C.V., Ciudad de México, 2023
 * VERADAT PROPRIETARY/CONFIDENCIAL. Use is subject to license terms.
 *
 * Project: veradat-node-manager
 * Module: adapter-rest
 * File: NodeManagerApi.java
 */

package com.bravo.credit.client.application.sync.api;



import com.bravo.credit.client.domain.model.Client;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

/** The interface Product controller. */
@RequestMapping("api/v1")
public interface ClientApi {




    /**
     * Gets process.
     *
     * @param enqueryNodeId the enquery node id
     * @return the process
     */
    @PostMapping(value = "/search/client",  produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(useReturnTypeSchema = true, responseCode = "200", description = "successful to get enquery node")
    ResponseEntity<Client> getProcess( @RequestHeader HttpHeaders header,
                                        @RequestBody String enqueryNodeId) throws Exception;



    /**
     * Gets process.
     *
     * @param enqueryNodeId the enquery node id
     * @return the process
     */
    @PostMapping(value = "created/client",  produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(useReturnTypeSchema = true, responseCode = "200", description = "successful to get enquery node")
    ResponseEntity<Void> createdClient( @RequestHeader HttpHeaders header,
                                        @RequestBody Client client) throws Exception;



}
