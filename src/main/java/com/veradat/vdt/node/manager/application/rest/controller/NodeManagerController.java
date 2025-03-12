/*
 * D. R. © Veradat Smart Network, S.A.P.I de C.V., Ciudad de México, 2023
 * VERADAT PROPRIETARY/CONFIDENCIAL. Use is subject to license terms.
 *
 * Project: veradat-node-manager
 * Module: adapter-rest
 * File: NodeManagerController.java
 */

package com.veradat.vdt.node.manager.application.rest.controller;

import com.veradat.commons.exception.VeradatException;
import com.veradat.commons.exception.utils.IdentifierManager;
import com.veradat.commons.message.Logger.LoggerService;
import com.veradat.vdt.node.manager.domain.inputport.NodeMappingAsyncInputPort;
import com.veradat.vdt.node.manager.domain.model.KeyResponseDTO;
import com.veradat.vdt.node.manager.domain.model.Mapping;
import com.veradat.vdt.node.manager.domain.model.NodeMapping;
import com.veradat.vdt.node.manager.domain.model.NodeMappingCommand;
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

    private final LoggerService logger = LoggerService.getLogger(NodeManagerController.class);
    private final NodeMappingAsyncInputPort nodeMappingAsyncInputPort;

    @Autowired
    public NodeManagerController(NodeMappingAsyncInputPort nodeMappingAsyncInputPort) {
        this.nodeMappingAsyncInputPort = nodeMappingAsyncInputPort;
    }

    public ResponseEntity<List<NodeMapping>> postNodeMappingRequest(NodeMappingCommand command) throws VeradatException {
        IdentifierManager.registerMethodIdentifier("postNodeMappingRequest", "PNMR");
        List<NodeMapping> mappings =
                nodeMappingAsyncInputPort.createNodeMapping(
                        command.getOriginNodeId(), command.getProcessId(), command.getNodes());
        HttpStatus status = mappings.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(mappings, status);
    }

    @Override
    public ResponseEntity<NodeMapping> getNode(String originNode) {
        IdentifierManager.registerMethodIdentifier("getNode", "GN");

        NodeMapping node = nodeMappingAsyncInputPort.getNode(originNode);

        return new ResponseEntity<>(node, HttpStatus.OK);
    }

    public ResponseEntity<Mapping> getProcess(String enqueryNodeId) throws VeradatException {
        IdentifierManager.registerMethodIdentifier("getProcess", "GP");

        Mapping nodeMapping = nodeMappingAsyncInputPort.getProcessId(enqueryNodeId);
        if (nodeMapping == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(nodeMapping, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<KeyResponseDTO> getPublicKey(
            String nodeMappingId, boolean isConversationOrigin, String processType)
            throws VeradatException {
        IdentifierManager.registerMethodIdentifier("getPublicKey", "GPK");
        logger.debug("VNM.NMC.PNMR.debug.1",
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
