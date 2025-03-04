/*
 * D. R. © Veradat Smart Network, S.A.P.I de C.V., Ciudad de México, 2023
 * VERADAT PROPRIETARY/CONFIDENCIAL. Use is subject to license terms.
 *
 * Project: veradat-node-manager
 * Module: adapter-persistence-spring-data-jpa
 * File: ProductSpringJpaAdapter.java
 */

package com.veradat.vdt.node.manager.infrastructure.adapter;

import com.veradat.commons.exception.VeradatException;
import com.veradat.commons.exception.VeradatRuntimeException;
import com.veradat.commons.exception.utils.IdentifierManager;
import com.veradat.commons.exception.utils.MapUtils;
import com.veradat.commons.exception.utils.VeradatDBExceptionFormatter;
import com.veradat.commons.message.Logger.LoggerService;
import com.veradat.vdt.node.manager.domain.model.Mapping;
import com.veradat.vdt.node.manager.domain.model.NodeMapping;
import com.veradat.vdt.node.manager.domain.outputport.PersistencePort;
import com.veradat.vdt.node.manager.infrastructure.entity.NodeMappingEntity;
import com.veradat.vdt.node.manager.infrastructure.repository.NodeMappingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Product spring jpa adapter.
 */
@Service
public class ProductSpringJpaAdapter implements PersistencePort {

    {
        IdentifierManager.registerClassIdentifier(ProductSpringJpaAdapter.class,"PSJA");
    }

    private final LoggerService logger = LoggerService.getLogger(ProductSpringJpaAdapter.class);

    private final NodeMappingRepository nodeMappingRepository;

    @Autowired
    public ProductSpringJpaAdapter(
        NodeMappingRepository nodeMappingRepository
    ){
        this.nodeMappingRepository = nodeMappingRepository;
    }

    public void registerNodeMappingList(String originNodeId, String enqueryId, List<NodeMapping> listNodeMapping) throws VeradatException {
        IdentifierManager.registerMethodIdentifier("registerNodeMappingList","RNML");

        NodeMappingEntity nodeMappingEntity;

        try {


        for (NodeMapping nodeMapping : listNodeMapping) {
            nodeMappingEntity = new NodeMappingEntity();
            nodeMappingEntity.setOriginInstitution(originNodeId);
            nodeMappingEntity.setDestinyInstitution(nodeMapping.getNodeId());
            nodeMappingEntity.setProcessId(enqueryId);
            nodeMappingEntity.setDestinyMapping(nodeMapping.getEnqueryNodeId());
            nodeMappingRepository.save(nodeMappingEntity);
        }
        } catch (VeradatRuntimeException e) {
            VeradatDBExceptionFormatter.formatDBCommonExceptions(logger.except("VNM.PSJA.RNML.debug.15",
                    "Ocurrio un error al registrar el mapeo de nodos"),
                    e, 15, MapUtils.nullableValueMap("originNodeId", originNodeId,"enqueryId",enqueryId),"PSJA","RNML");
        }
    }

    public Mapping getProcessId(String enqueryNodeId) throws VeradatException {
        IdentifierManager.registerMethodIdentifier("getProcessId","GPI");

        NodeMappingEntity nodeMappingEntity = nodeMappingRepository.findByDestinyMapping(enqueryNodeId);

        Mapping mapping = new Mapping();
        try {


        if (nodeMappingEntity == null) {
            return null;
        }
            mapping.setNodeMappingId(nodeMappingEntity.getNodeMappingId());
            mapping.setOriginInstitution(nodeMappingEntity.getOriginInstitution());
            mapping.setDestinyInstitution(nodeMappingEntity.getDestinyInstitution());
            mapping.setProcessId(nodeMappingEntity.getProcessId());
            mapping.setDestinyMapping(nodeMappingEntity.getDestinyMapping());

        } catch (VeradatRuntimeException e) {
            VeradatDBExceptionFormatter.formatDBCommonExceptions(logger.except( "VNM.PSJA.RNML.debug.15",
                            "Ocurrio un error al obtener el proceso de mapeo"),
                    e, 15, MapUtils.nullableValueMap("enqueryNodeId", enqueryNodeId),"PSJA","GPI");
        }
        return mapping;
    }

    @Override
    public void persistNodeMappings(List<Mapping> nodeMappings) throws VeradatException {
        IdentifierManager.registerMethodIdentifier("persistNodeMappings","PNMR");

        try {

        for (Mapping nodeMappingPersistence : nodeMappings) {
            NodeMappingEntity nodeMappingEntity = new NodeMappingEntity();
            nodeMappingEntity.setOriginInstitution(nodeMappingPersistence.getOriginInstitution());
            nodeMappingEntity.setDestinyInstitution(nodeMappingPersistence.getDestinyInstitution());
            nodeMappingEntity.setProcessId(nodeMappingPersistence.getProcessId());
            nodeMappingEntity.setDestinyMapping(nodeMappingPersistence.getDestinyMapping());
            nodeMappingRepository.saveAndFlush(nodeMappingEntity);
        }
        } catch (VeradatRuntimeException e) {
            VeradatDBExceptionFormatter.formatDBCommonExceptions(logger.except("VNM.PSJA.RNML.debug.15",
                            "Ocurrio un error al persistir el mapeo de nodos"),
                    e, 15, MapUtils.nullableValueMap("nodeMappings", nodeMappings),"PSJA","PNMR");
        }
    }
}
