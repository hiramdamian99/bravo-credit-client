/*
 * D. R. © Veradat Smart Network, S.A.P.I de C.V., Ciudad de México, 2023
 * VERADAT PROPRIETARY/CONFIDENCIAL. Use is subject to license terms.
 *
 * Project: veradat-node-manager
 * Module: domain
 * File: NodeMappingUseCase.java
 */

package com.veradat.vdt.node.manager.domain.usecases;

import com.veradat.commons.exception.VeradatException;
import com.veradat.commons.exception.utils.IdentifierManager;
import com.veradat.commons.exception.utils.MapUtils;
import com.veradat.commons.message.Logger.LoggerService;
import com.veradat.lib.util.UUIDGenerator;
import com.veradat.vdt.node.manager.domain.inputport.NodeMappingAsyncInputPort;
import com.veradat.vdt.node.manager.domain.model.KeyResponseDTO;
import com.veradat.vdt.node.manager.domain.model.Mapping;
import com.veradat.vdt.node.manager.domain.model.NodeMapping;
import com.veradat.vdt.node.manager.domain.outputport.PersistencePort;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

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


    private final LoggerService logger = LoggerService.getLogger(NodeMappingUseCase.class);

    /**
     * logger
     */


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

    public List<NodeMapping> createNodeMapping(String originNodeId, String enqueryId, @NotNull List<String> nodes) throws VeradatException {
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



    /**
     * Return new node mapping.
     *
     * @param originNode the origin node
     * @return the enquery node
     */

    @Override
    public NodeMapping getNode(String originNode) {
        IdentifierManager.registerMethodIdentifier("getNode","GN");
        return new NodeMapping(UUIDGenerator.generateVeradatUUID(), originNode, RandomStringUtils.randomAlphabetic(3));
    }



    /**
     * Return new node mapping.
     *
     * @param enqueryNodeId the origin node
     * @return the enquery node Id
     */

    public Mapping getProcessId(String enqueryNodeId) throws VeradatException {
        IdentifierManager.registerMethodIdentifier("getProcessId","GPI");
        return persistencePort.getProcessId(enqueryNodeId);
    }






    /**
     * Return new node mapping.
     *
     * @param nodeMappingId the node mapping id
     * @param isConversationOrigin the is conversation origin
     * @param processType the process type to persist
     * @return the key
     */
    @Override
    public KeyResponseDTO getKeyAlias(String nodeMappingId, boolean isConversationOrigin, String processType) throws VeradatException {
        IdentifierManager.registerMethodIdentifier("getKeyAlias","GKA");

        logger.debug("VNM.NMU.GKA.debug.1",
                "Iniciando proceso de la obtención de la llave pública con los datos: {}, {}, {}",
            nodeMappingId, isConversationOrigin, processType);



        KeyResponseDTO keyResponse = new KeyResponseDTO();

        Mapping mapping = getProcessId(nodeMappingId);

        if(mapping==null)
            throw new VeradatException(logger.except("VNM.NMU.GKA.EXCEPTION.1",
                    "Ocurrio un error al intentar consultar el mapeo del nodo"),true,1,
                MapUtils.nullableValueMap("nodeMappingId",nodeMappingId,
                        "isConversationOrigin",isConversationOrigin,"processType",processType));


        if (((processType.equals("EPTE") || processType.equals("EPTM")) &&
                isConversationOrigin) || (processType.equals("EPTA") && !isConversationOrigin)) {
            logger.debug("VNM.NMU.GKA.debug.2", "Caso en el que es una búsqueda y es el origen de la conversacion");
            keyResponse.setOriginNode(mapping.getOriginInstitution());
            keyResponse.setKeyNode(mapping.getDestinyInstitution());
        } else if ((processType.equals("EPTA") && isConversationOrigin) || ((processType.equals("EPTE") || processType.equals("EPTM")) && !isConversationOrigin)) {
            logger.debug("VNM.NMU.GKA.debug.3","Caso en el que es una alerta y es el origen de la conversacion");
            keyResponse.setOriginNode(mapping.getDestinyInstitution());
            keyResponse.setKeyNode(mapping.getOriginInstitution());
        } else {
            throw new ValidationException(logger.except("VNM.NMU.GKA.EXCEPTION.2","Los datos ingresados no concuerdan"));
        }

        return keyResponse;
    }



    /**
     * Register node mapping list.
     *
     * @param nodeMappings the origin node ids
     *
     */
    @Override
    public void persistNodeMappings(List<Mapping> nodeMappings) throws VeradatException {
        IdentifierManager.registerMethodIdentifier("persistNodeMappings","PNM");
        logger.debug("VNM.NMU.PNM.debug.1", "Persistiendo mapeo de nodos");
        persistencePort.persistNodeMappings(nodeMappings);
    }
}
