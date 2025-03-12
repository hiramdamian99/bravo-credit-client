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
     * Create node mapping list.
     *
     * @param originIp  the origin ip
     * @param enqueryId the enquery id
     * @param nodes     the nodes
     *
     * @return the list
     */
    List<NodeMapping> createNodeMapping(String originIp, String enqueryId, @NotNull List<String> nodes) throws VeradatException;

    /**
     * Gets node.
     *
     * @param originNode the origin node
     *
     * @return the node
     */
    NodeMapping getNode(String originNode);

    /**
     * Gets process id.
     *
     * @param enqueryNodeId the enquery node id
     *
     * @return the process id
     */
    Mapping getProcessId(String enqueryNodeId) throws VeradatException;

    /**
     * Gets key alias.
     *
     * @param nodeMappingId        the node mapping id
     * @param isConversationOrigin the is conversation origin
     * @param processType          the process type
     *
     * @return the key alias
     */
    KeyResponseDTO getKeyAlias(String nodeMappingId, boolean isConversationOrigin, String processType) throws VeradatException;

    /**
     * Persist node mappings.
     *
     * @param nodeMappings the node mappings
     */
    void persistNodeMappings(List<Mapping> nodeMappings) throws VeradatException;

}
