/*
 * D. R. © Veradat Smart Network, S.A.P.I de C.V., Ciudad de México, 2023
 * VERADAT PROPRIETARY/CONFIDENCIAL. Use is subject to license terms.
 *
 * Project: veradat-node-manager
 * Module: adapter-persistence-spring-data-jpa
 * File: KeyNameRepository.java
 */

package com.veradat.node.manager.persistence.jpa.repository;

import com.veradat.node.manager.persistence.jpa.entity.KeyNameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Key name repository.
 */
public interface KeyNameRepository extends JpaRepository<KeyNameEntity, Integer>
{

    /**
     * Find by node id key name entity.
     *
     * @param nodeId the node id
     *
     * @return the key name entity
     */
    KeyNameEntity findByNodeId(String nodeId);
}
