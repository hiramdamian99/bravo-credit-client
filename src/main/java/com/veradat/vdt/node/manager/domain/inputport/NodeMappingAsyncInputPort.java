/*
 * D. R. © Veradat Smart Network, S.A.P.I de C.V., Ciudad de México, 2023
 * VERADAT PROPRIETARY/CONFIDENCIAL. Use is subject to license terms.
 *
 * Project: veradat-node-manager
 * File: NodeMappingAsyncInputPort.java
 */

package com.veradat.vdt.node.manager.domain.inputport;


import com.veradat.commons.exception.VeradatException;
import com.veradat.vdt.node.manager.domain.model.Mapping;

import java.util.List;

/**
 * The interface Node mapping async input port.
 * @Author: Hiram Lopez Damian
 * @LastContributor: Hiram Lopez Damian
 * @Created At: 05/03/2025
 * @Updated At: 13/01/2026
 */
public interface NodeMappingAsyncInputPort {



    /**
     * Gets process id.
     *
     * @param enqueryNodeId the enquery node id
     *
     * @return the process id
     */
    Mapping getByDestinyMapping(String enqueryNodeId) throws VeradatException;


    /**
     * Persist node mappings.
     *
     * @param nodeMappings the node mappings
     */
    void persistNodeMappings(List<Mapping> nodeMappings) throws VeradatException;

}
