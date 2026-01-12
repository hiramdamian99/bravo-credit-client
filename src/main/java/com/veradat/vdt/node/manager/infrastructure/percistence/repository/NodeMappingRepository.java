/*
 * D. R. © Veradat Smart Network, S.A.P.I de C.V., Ciudad de México, 2023
 * VERADAT PROPRIETARY/CONFIDENCIAL. Use is subject to license terms.
 *
 * Project: veradat-node-manager
 * Module: adapter-persistence-spring-data-jpa
 * File: NodeMappingRepository.java
 */

package com.veradat.vdt.node.manager.infrastructure.percistence.repository;

import com.veradat.vdt.node.manager.infrastructure.percistence.entity.NodeMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Node mapping repository.
 */
public interface NodeMappingRepository extends JpaRepository<NodeMappingEntity, Integer>
{
    /**
     * Find by destiny mapping node mapping entity.
     *
     * @param enqueryNodeId the enquery node id
     *
     * @return the node mapping entity
     */
    NodeMappingEntity findByDestinyMapping(String enqueryNodeId);
}
