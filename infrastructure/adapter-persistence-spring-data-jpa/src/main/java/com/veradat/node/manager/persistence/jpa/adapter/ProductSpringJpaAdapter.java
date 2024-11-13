/*
 * D. R. © Veradat Smart Network, S.A.P.I de C.V., Ciudad de México, 2023
 * VERADAT PROPRIETARY/CONFIDENCIAL. Use is subject to license terms.
 *
 * Project: veradat-node-manager
 * Module: adapter-persistence-spring-data-jpa
 * File: ProductSpringJpaAdapter.java
 */

package com.veradat.node.manager.persistence.jpa.adapter;

import com.veradat.commons.exception.utils.IdentifierManager;
import com.veradat.node.manager.persistence.jpa.entity.KeyNameEntity;
import com.veradat.node.manager.persistence.jpa.entity.NodeMappingEntity;
import com.veradat.node.manager.persistence.jpa.repository.KeyNameRepository;
import com.veradat.node.manager.persistence.jpa.repository.NodeMappingRepository;
import com.veradat.nodemanager.domain.model.KeyName;
import com.veradat.nodemanager.domain.model.Mapping;
import com.veradat.nodemanager.domain.model.NodeMapping;
import com.veradat.nodemanager.domain.outputport.PersistencePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Product spring jpa adapter.
 */
@Service
public class ProductSpringJpaAdapter implements PersistencePort
{

    private final Logger logger = LoggerFactory.getLogger(ProductSpringJpaAdapter.class);

    private final NodeMappingRepository nodeMappingRepository;

    private final KeyNameRepository keyNameRepository;

    @Autowired
    public ProductSpringJpaAdapter(
        NodeMappingRepository nodeMappingRepository,
        KeyNameRepository keyNameRepository
    ){
        this.nodeMappingRepository = nodeMappingRepository;
        this.keyNameRepository = keyNameRepository;
    }

    public void registerNodeMappingList(String originNodeId, String enqueryId, List<NodeMapping> listNodeMapping) {
        IdentifierManager.registerMethodIdentifier("registerNodeMappingList","RNML");

        NodeMappingEntity nodeMappingEntity;
        for (NodeMapping nodeMapping : listNodeMapping) {
            nodeMappingEntity = new NodeMappingEntity();
            nodeMappingEntity.setOriginInstitution(originNodeId);
            nodeMappingEntity.setDestinyInstitution(nodeMapping.getNodeId());
            nodeMappingEntity.setProcessId(enqueryId);
            nodeMappingEntity.setDestinyMapping(nodeMapping.getEnqueryNodeId());
            nodeMappingRepository.save(nodeMappingEntity);
        }
    }

    public Mapping getProcessId(String enqueryNodeId) {
        IdentifierManager.registerMethodIdentifier("getProcessId","GPI");

        NodeMappingEntity nodeMappingEntity = nodeMappingRepository.findByDestinyMapping(enqueryNodeId);

        if (nodeMappingEntity == null) {
            return null;
        }
        return new Mapping(
            nodeMappingEntity.getNodeMappingId(),
            nodeMappingEntity.getOriginInstitution(),
            nodeMappingEntity.getDestinyInstitution(),
            nodeMappingEntity.getProcessId(),
            nodeMappingEntity.getDestinyMapping()
        );
    }

    @Override
    public KeyName getAliasKey(String nodeId) {
        IdentifierManager.registerMethodIdentifier("getKeyAlias","GKA");

        KeyNameEntity keyNameEntity = new KeyNameEntity();

        KeyName keyName = new KeyName();

        try {
            logger.info("Consultando la BD");

            keyNameEntity = keyNameRepository.findByNodeId(nodeId);
        } catch (Exception e) {

            logger.error("Error al consultar la BD", e);
        }

        if (keyNameEntity != null) {
            keyName.setId(keyNameEntity.getId());
            keyName.setNodeId(keyNameEntity.getNodeId());
            keyName.setPublicKeyRoute(keyNameEntity.getPublicKeyRoute());
            keyName.setPublicKeyAlias(keyNameEntity.getPublicKeyAlias());
            keyName.setIsActive(keyNameEntity.getIsActive());
            keyName.setActiveStartDate(keyNameEntity.getActiveStartDate());
            keyName.setActiveEndDate(keyNameEntity.getActiveEndDate());
        }

        return keyName;
    }

    @Override
    public void persistNodeMappings(List<Mapping> nodeMappings) {
        IdentifierManager.registerMethodIdentifier("persistNodeMappings","PNMR");

        for (Mapping nodeMappingPersistence : nodeMappings) {
            NodeMappingEntity nodeMappingEntity = new NodeMappingEntity();
            nodeMappingEntity.setOriginInstitution(nodeMappingPersistence.getOriginInstitution());
            nodeMappingEntity.setDestinyInstitution(nodeMappingPersistence.getDestinyInstitution());
            nodeMappingEntity.setProcessId(nodeMappingPersistence.getProcessId());
            nodeMappingEntity.setDestinyMapping(nodeMappingPersistence.getDestinyMapping());
            nodeMappingRepository.saveAndFlush(nodeMappingEntity);
        }
    }
}
