/*
 * D. R. © Veradat Smart Network, S.A.P.I de C.V., Ciudad de México, 2023
 * VERADAT PROPRIETARY/CONFIDENCIAL. Use is subject to license terms.
 *
 * Project: veradat-node-manager
 * Module: domain
 * File: PersistencePort.java
 */

package com.veradat.nodemanager.domain.outputport;

import com.veradat.nodemanager.domain.model.KeyName;
import com.veradat.nodemanager.domain.model.Mapping;
import com.veradat.nodemanager.domain.model.NodeMapping;

import java.util.List;

/**
 * The interface Persistence port.
 */
public interface PersistencePort
{

    /**
     * Register node mapping list.
     *
     * @param originNodeId the origin node id
     * @param enqueryId    the enquery id
     * @param nodeMapping  the node mapping
     */
    void registerNodeMappingList(String originNodeId, String enqueryId, List<NodeMapping> nodeMapping);

    /**
     * Gets process id.
     *
     * @param enqueryNodeId the enquery node id
     *
     * @return the process id
     */
    Mapping getProcessId(String enqueryNodeId);

    /**
     * Gets alias key.
     *
     * @param nodeId the node id
     *
     * @return the alias key
     */
    KeyName getAliasKey(String nodeId);

    void persistNodeMappings(List<Mapping> nodeMappings);
}
