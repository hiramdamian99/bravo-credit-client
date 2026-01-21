/*
 * D. R. © Hiram Solutions de C.V., Ciudad de México, 2026
 * CONFIDENTIAL Use is subject to license terms.
 *
 * Project: bravo-credit-client
 * File: ClientApi.java
 */
package com.bravo.credit.client.application.sync.api;



import com.bravo.credit.client.domain.model.Client;
import com.bravo.credit.client.domain.model.ClientRequest;
import com.bravo.credit.client.domain.model.ClientResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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
    ResponseEntity<List<ClientResponse>> getProcess(@RequestHeader HttpHeaders header,
                                                    @RequestBody ClientRequest client) throws Exception;



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
