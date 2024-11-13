/*
 * D. R. © Veradat Smart Network, S.A.P.I de C.V., Ciudad de México, 2023
 * VERADAT PROPRIETARY/CONFIDENCIAL. Use is subject to license terms.
 *
 * Project: veradat-node-manager
 * Module: adapter-rest
 * File: NodeManagerController.java
 */

package com.veradat.node.manager.infrastructure.rest.controller;

import com.veradat.commons.exception.utils.IdentifierManager;
import com.veradat.nodemanager.domain.exception.NotFoundException;
import com.veradat.nodemanager.domain.inputport.NodeMappingAsyncInputPort;
import com.veradat.nodemanager.domain.model.KeyResponseDTO;
import com.veradat.nodemanager.domain.model.Mapping;
import com.veradat.nodemanager.domain.model.NodeMapping;
import com.veradat.nodemanager.domain.model.NodeMappingCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** The type Product controller. */
@RestController
public class NodeManagerController implements NodeManagerApi {

    static {
        IdentifierManager.registerClassIdentifier(NodeManagerController.class, "NMC");
    }

    private final Logger logger = LoggerFactory.getLogger(NodeManagerController.class);
    private final NodeMappingAsyncInputPort nodeMappingAsyncInputPort;

    @Autowired
    public NodeManagerController(NodeMappingAsyncInputPort nodeMappingAsyncInputPort) {
        this.nodeMappingAsyncInputPort = nodeMappingAsyncInputPort;
    }

    public ResponseEntity<List<NodeMapping>> postNodeMappingRequest(NodeMappingCommand command) {
        IdentifierManager.registerMethodIdentifier("postNodeMappingRequest", "PNMR");
        logger.info("postNodeMappingRequest");
        List<NodeMapping> mappings =
                nodeMappingAsyncInputPort.createNodeMapping(
                        command.getOriginNodeId(), command.getProcessId(), command.getNodes());
        HttpStatus status = mappings.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(mappings, status);
    }

    @Override
    public ResponseEntity<NodeMapping> getNode(String originNode) {
        IdentifierManager.registerMethodIdentifier("getNode", "GN");

        logger.info("getNode");
        NodeMapping node = nodeMappingAsyncInputPort.getNode(originNode);

        return new ResponseEntity<>(node, HttpStatus.OK);
    }

    public ResponseEntity<Mapping> getProcess(String enqueryNodeId) {
        IdentifierManager.registerMethodIdentifier("getProcess", "GP");

        logger.info("getProcess");
        Mapping nodeMapping = nodeMappingAsyncInputPort.getProcessId(enqueryNodeId);
        if (nodeMapping == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(nodeMapping, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<KeyResponseDTO> getPublicKey(
            String nodeMappingId, boolean isConversationOrigin, String processType)
            throws NotFoundException {
        IdentifierManager.registerMethodIdentifier("getPublicKey", "GPK");
        logger.info(
                "Iniciando el proceso de consultar la llave pública con los datos: {}, {}, {}",
                nodeMappingId,
                isConversationOrigin,
                processType);
        KeyResponseDTO keyresponse =
                nodeMappingAsyncInputPort.getKeyAlias(
                        nodeMappingId, isConversationOrigin, processType);
        if (keyresponse == null) {
            return new ResponseEntity<>(new KeyResponseDTO(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(keyresponse, HttpStatus.OK);
    }
}
