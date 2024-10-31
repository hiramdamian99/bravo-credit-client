/*
 * D. R. © Veradat Smart Network, S.A.P.I de C.V., Ciudad de México, 2023
 * VERADAT PROPRIETARY/CONFIDENCIAL. Use is subject to license terms.
 *
 * Project: veradat-node-manager
 * Module: domain
 * File: NodeMappingUseCase.java
 */

package com.veradat.nodemanager.domain.usecases;

import com.veradat.commons.exception.utils.IdentifierManager;
import com.veradat.commons.uuidgenerator.UUIDGenerator;
import com.veradat.nodemanager.domain.exception.NotFoundException;
import com.veradat.nodemanager.domain.inputport.NodeMappingAsyncInputPort;
import com.veradat.nodemanager.domain.model.KeyResponseDTO;
import com.veradat.nodemanager.domain.model.Mapping;
import com.veradat.nodemanager.domain.model.NodeMapping;
import com.veradat.nodemanager.domain.outputport.PersistencePort;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    /**
     * logger
     */
    private final Logger logger = LoggerFactory.getLogger(NodeMappingUseCase.class);

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

    public List<NodeMapping> createNodeMapping(String originNodeId, String enqueryId, @NotNull List<String> nodes) {
        IdentifierManager.registerMethodIdentifier("createNodeMapping","CNM");

        List<NodeMapping> items = nodes
            .stream()
            .filter(node -> !StringUtils.equals(node, originNodeId))
            .map(node -> new NodeMapping(
                UUIDGenerator.generateVeradatUUID(), node, RandomStringUtils.randomAlphabetic(3))
            )
            .toList();

        persistencePort.registerNodeMappingList(originNodeId, enqueryId, items);

        return items;
    }

    @Override
    public NodeMapping getNode(String originNode) {
        IdentifierManager.registerMethodIdentifier("getNode","GN");
        return new NodeMapping(UUIDGenerator.generateVeradatUUID(), originNode, RandomStringUtils.randomAlphabetic(3));
    }

    public Mapping getProcessId(String enqueryNodeId) {
        IdentifierManager.registerMethodIdentifier("getProcessId","GPI");
        return persistencePort.getProcessId(enqueryNodeId);
    }

    @Override
    public KeyResponseDTO getKeyAlias(String nodeMappingId, boolean isConversationOrigin, String processType) throws NotFoundException {
        IdentifierManager.registerMethodIdentifier("getKeyAlias","GKA");

        logger.info("Iniciando proceso de la obtención de la llave pública con los datos: {}, {}, {}",
            nodeMappingId, isConversationOrigin, processType
        );
        KeyResponseDTO keyResponse = new KeyResponseDTO();

        Mapping mapping = getProcessId(nodeMappingId);

        if(mapping==null)
            throw new NotFoundException("Ocurrio un error al intentar consultar el mapeo del nodo",1);


        if ((processType.equals("EPTE") && isConversationOrigin) || (processType.equals("EPTA") && !isConversationOrigin)) {
            logger.info("Caso en el que es una búsqueda y es el origen de la conversacion");
            keyResponse.setOriginNode(mapping.getOriginInstitution());
            keyResponse.setKeyNode(mapping.getDestinyInstitution());
        } else if ((processType.equals("EPTA") && isConversationOrigin) || (processType.equals("EPTE") && !isConversationOrigin)) {
            logger.info("Caso en el que es una alerta y es el origen de la conversacion");
            keyResponse.setOriginNode(mapping.getDestinyInstitution());
            keyResponse.setKeyNode(mapping.getOriginInstitution());
        } else {
            throw new ValidationException("Los datos ingresados no concuerdan");
        }

        return keyResponse;
    }

    @Override
    public void persistNodeMappings(List<Mapping> nodeMappings) {
        IdentifierManager.registerMethodIdentifier("persistNodeMappings","PNM");
        logger.info("Persistiendo mapeo de nodos");
        persistencePort.persistNodeMappings(nodeMappings);
    }
}
