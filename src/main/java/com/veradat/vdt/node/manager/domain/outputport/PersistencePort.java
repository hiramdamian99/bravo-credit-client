/*
 * D. R. © Veradat Smart Network, S.A.P.I de C.V., Ciudad de México, 2023
 * VERADAT PROPRIETARY/CONFIDENCIAL. Use is subject to license terms.
 *
 * Project: veradat-node-manager
 * Module: domain
 * File: PersistencePort.java
 */

package com.veradat.vdt.node.manager.domain.outputport;

import com.veradat.commons.exception.VeradatException;
import com.veradat.vdt.node.manager.domain.model.Mapping;
import com.veradat.vdt.node.manager.domain.model.NodeMapping;

import java.util.List;

/**
 * The interface Persistence port.
 */
public interface PersistencePort
{


    /**
     * Gets process id.
     *
     * @param enqueryNodeId the enquery node id
     *
     * @return the process id
     */
    Mapping getProcessId(String enqueryNodeId) throws VeradatException;

    void persistNodeMappings(List<Mapping> nodeMappings) throws VeradatException;
}
