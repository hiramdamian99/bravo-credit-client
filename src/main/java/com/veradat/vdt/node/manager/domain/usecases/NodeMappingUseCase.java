/*
 * D. R. © Veradat Smart Network, S.A.P.I de C.V., Ciudad de México, 2023
 * VERADAT PROPRIETARY/CONFIDENCIAL. Use is subject to license terms.
 *
 * Project: veradat-node-manager
 * Module: domain
 * File: NodeMappingUseCase.java
 */

package com.veradat.vdt.node.manager.domain.usecases;

import com.veradat.commons.exception.VeradatException;
import com.veradat.commons.exception.utils.IdentifierManager;
import com.veradat.commons.message.Logger.LoggerService;
import com.veradat.vdt.node.manager.domain.inputport.NodeMappingAsyncInputPort;
import com.veradat.vdt.node.manager.domain.model.Mapping;
import com.veradat.vdt.node.manager.domain.outputport.PersistencePort;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Node mapping use case.
 */
@Service
public class NodeMappingUseCase implements NodeMappingAsyncInputPort
{

    static {
        IdentifierManager.registerClassIdentifier(NodeMappingUseCase.class,"NMU");
    }


    private final LoggerService logger = LoggerService.getLogger(NodeMappingUseCase.class);

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
    public NodeMappingUseCase(PersistencePort persistencePort)
    {
        this.persistencePort = persistencePort;
    }

    /**
     * Return new node mapping.
     *
     * @param enqueryNodeId the origin node
     * @return the enquery node Id
     */

    public Mapping getProcessId(String enqueryNodeId) throws VeradatException {
        IdentifierManager.registerMethodIdentifier("getProcessId","GPI");
        return persistencePort.getProcessId(enqueryNodeId);
    }



    /**
     * Register node mapping list.
     *
     * @param nodeMappings the origin node ids
     *
     */

    @Override
    public void persistNodeMappings(List<Mapping> nodeMappings) throws VeradatException {
        IdentifierManager.registerMethodIdentifier("persistNodeMappings","PNM");
        logger.debug("VNM.NMU.PNM.debug.1", "Persistiendo mapeo de nodos");
        persistencePort.persistNodeMappings(nodeMappings);
    }


}
