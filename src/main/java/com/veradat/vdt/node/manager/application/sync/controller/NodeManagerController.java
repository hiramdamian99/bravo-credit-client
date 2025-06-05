/*
 * D. R. © Veradat Smart Network, S.A.P.I de C.V., Ciudad de México, 2023
 * VERADAT PROPRIETARY/CONFIDENCIAL. Use is subject to license terms.
 *
 * Project: veradat-node-manager
 * Module: adapter-rest
 * File: NodeManagerController.java
 */

package com.veradat.vdt.node.manager.application.sync.controller;

import com.veradat.commons.exception.VeradatException;
import com.veradat.commons.exception.utils.IdentifierManager;
import com.veradat.commons.message.Logger.LoggerService;
import com.veradat.lib.security.annotations.VeradatAuthority;
import com.veradat.lib.security.model.AuditScope;
import com.veradat.vdt.node.manager.application.sync.api.NodeManagerApi;
import com.veradat.vdt.node.manager.domain.inputport.NodeMappingAsyncInputPort;
import com.veradat.vdt.node.manager.domain.model.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/** The type Product controller.
 * This class is the controller for the NodeManager
 *
 */
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



    /**
     * This method is used to get a process
     * @param enqueryNodeId the enquery node id
     * @return Mapping
     */
    @VeradatAuthority(hasPermissions = {"GPM"}, auditScope = AuditScope.AUDIT_ALL)
    @Override
    public ResponseEntity<Mapping> getProcess(HttpHeaders header,
                                              String enqueryNodeId) throws VeradatException {
        IdentifierManager.registerMethodIdentifier("getProcess", "GP");

        Mapping nodeMapping = nodeMappingAsyncInputPort.getProcessId(enqueryNodeId);
        if (nodeMapping == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(nodeMapping, HttpStatus.OK);
    }



}
