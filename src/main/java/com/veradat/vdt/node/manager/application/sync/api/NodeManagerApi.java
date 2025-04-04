/*
 * D. R. © Veradat Smart Network, S.A.P.I de C.V., Ciudad de México, 2023
 * VERADAT PROPRIETARY/CONFIDENCIAL. Use is subject to license terms.
 *
 * Project: veradat-node-manager
 * Module: adapter-rest
 * File: NodeManagerApi.java
 */

package com.veradat.vdt.node.manager.application.sync.api;


import com.veradat.commons.exception.VeradatException;
import com.veradat.vdt.node.manager.domain.model.Mapping;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/** The interface Product controller. */
public interface NodeManagerApi {




    /**
     * Gets process.
     *
     * @param enqueryNodeId the enquery node id
     * @return the process
     */
    @GetMapping(value = "/node-mapping/mapping/{enqueryNodeId}",  produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(useReturnTypeSchema = true, responseCode = "200", description = "successful to get enquery node")
    ResponseEntity<Mapping> getProcess(@PathVariable String enqueryNodeId) throws VeradatException;



}
