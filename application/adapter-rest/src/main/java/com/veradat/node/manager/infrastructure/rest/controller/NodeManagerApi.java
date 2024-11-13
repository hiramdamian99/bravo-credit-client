/*
 * D. R. © Veradat Smart Network, S.A.P.I de C.V., Ciudad de México, 2023
 * VERADAT PROPRIETARY/CONFIDENCIAL. Use is subject to license terms.
 *
 * Project: veradat-node-manager
 * Module: adapter-rest
 * File: NodeManagerApi.java
 */

package com.veradat.node.manager.infrastructure.rest.controller;

import com.veradat.nodemanager.domain.exception.NotFoundException;
import com.veradat.nodemanager.domain.model.KeyResponseDTO;
import com.veradat.nodemanager.domain.model.Mapping;
import com.veradat.nodemanager.domain.model.NodeMapping;
import com.veradat.nodemanager.domain.model.NodeMappingCommand;
import jakarta.validation.constraints.NotBlank;
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
    @PostMapping("/node-mapping/create")
    ResponseEntity<List<NodeMapping>> postNodeMappingRequest(
            @RequestBody NodeMappingCommand command);

    /**
     * Gets node.
     *
     * @param originNode the origin node
     * @return the node
     */
    @GetMapping("/node-mapping/node/{originNode}")
    ResponseEntity<NodeMapping> getNode(@PathVariable String originNode);

    /**
     * Gets process.
     *
     * @param enqueryNodeId the enquery node id
     * @return the process
     */
    @GetMapping("/node-mapping/mapping/{enqueryNodeId}")
    ResponseEntity<Mapping> getProcess(@PathVariable String enqueryNodeId);

    /**
     * Gets public key.
     *
     * @param nodeMappingId the node mapping id
     * @param isConversationOrigin the is conversation origin
     * @param processType the process type
     * @return the public key
     */
    @GetMapping("/node-mapping/public-key/{nodeMappingId}/{isConversationOrigin}/{processType}")
    ResponseEntity<KeyResponseDTO> getPublicKey(
            @PathVariable
                    @NotBlank(message = "El id del mapeo no puede ser vacio")
                    String nodeMappingId,
            @PathVariable
                    boolean isConversationOrigin,
            @PathVariable
                    @NotBlank(message = "El tipo de proceso no puede ser vacio")
                    String processType)
            throws NotFoundException;
}
