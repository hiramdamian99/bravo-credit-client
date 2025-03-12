/*
 * D. R. © Veradat Smart Network, S.A.P.I de C.V., Ciudad de México, 2023
 * VERADAT PROPRIETARY/CONFIDENCIAL. Use is subject to license terms.
 *
 * Project: veradat-node-manager
 * Module: adapter-rest
 * File: NodeManagerApi.java
 */

package com.veradat.vdt.node.manager.application.rest.controller;


import com.veradat.commons.exception.VeradatException;
import com.veradat.vdt.node.manager.domain.model.KeyResponseDTO;
import com.veradat.vdt.node.manager.domain.model.Mapping;
import com.veradat.vdt.node.manager.domain.model.NodeMapping;
import com.veradat.vdt.node.manager.domain.model.NodeMappingCommand;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/** The interface Product controller. */
public interface NodeManagerApi {

    /**
     * Post node mapping request response entity.
     *
     * @param command the command
     * @return the response entity
     */
    @PostMapping(value = "/node-mapping/create",  produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(useReturnTypeSchema = true, responseCode = "200", description = "successful to created node mapping")
    ResponseEntity<List<NodeMapping>> postNodeMappingRequest(
            @RequestBody NodeMappingCommand command) throws VeradatException;

    /**
     * Gets node.
     *
     * @param originNode the origin node
     * @return the node
     */
    @GetMapping(value = "/node-mapping/node/{originNode}",  produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(useReturnTypeSchema = true, responseCode = "200", description = "successful to get node")
    ResponseEntity<NodeMapping> getNode(@PathVariable String originNode);

    /**
     * Gets process.
     *
     * @param enqueryNodeId the enquery node id
     * @return the process
     */
    @GetMapping(value = "/node-mapping/mapping/{enqueryNodeId}",  produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(useReturnTypeSchema = true, responseCode = "200", description = "successful to get enquery node")
    ResponseEntity<Mapping> getProcess(@PathVariable String enqueryNodeId) throws VeradatException;


    /**
     * Gets public key.
     *
     * @param nodeMappingId the node mapping id
     * @param isConversationOrigin the is conversation origin
     * @param processType the process type
     * @return the public key
     */
    @GetMapping(value = "/node-mapping/public-key/{nodeMappingId}/{isConversationOrigin}/{processType}",  produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(useReturnTypeSchema = true, responseCode = "200", description = "successful persict process")
    ResponseEntity<KeyResponseDTO> getPublicKey(
            @PathVariable @NotBlank(message = "{VNM.NMA.PK.MESSAGE.1:El id del mapeo no puede ser vacio}") String nodeMappingId,
            @PathVariable boolean isConversationOrigin,
            @PathVariable @NotBlank(message = "{VNM.NMA.PK.MESSAGE.2:El tipo de proceso no puede ser vacio}") String processType)
            throws VeradatException;
}
