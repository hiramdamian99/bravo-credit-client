/*
 * D. R. © Veradat Smart Network, S.A.P.I de C.V., Ciudad de México, 2023
 * VERADAT PROPRIETARY/CONFIDENCIAL. Use is subject to license terms.
 *
 * Project: veradat-node-manager
 * Module: domain
 * File: NodeMappingAsyncInputPort.java
 */

package com.veradat.vdt.node.manager.domain.inputport;


import com.veradat.commons.exception.VeradatException;
import com.veradat.vdt.node.manager.domain.model.KeyResponseDTO;
import com.veradat.vdt.node.manager.domain.model.Mapping;
import com.veradat.vdt.node.manager.domain.model.NodeMapping;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * The interface Node mapping async input port.
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
