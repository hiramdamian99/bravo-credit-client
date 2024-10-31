/*
 * D. R. © Veradat Smart Network, S.A.P.I de C.V., Ciudad de México, 2023
 * VERADAT PROPRIETARY/CONFIDENCIAL. Use is subject to license terms.
 *
 * Project: veradat-node-manager
 * Module: domain
 * File: NodeMappingUseCaseTest.java
 */

package com.veradat.nodemanager.domain.usecases;

import com.veradat.nodemanager.domain.exception.NotFoundException;
import com.veradat.nodemanager.domain.model.KeyResponseDTO;
import com.veradat.nodemanager.domain.model.Mapping;
import com.veradat.nodemanager.domain.model.NodeMapping;
import com.veradat.nodemanager.domain.outputport.PersistencePort;
import jakarta.validation.ValidationException;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NodeMappingUseCaseTest
{
    @InjectMocks
    public NodeMappingUseCase nodeMappingUseCase;

    @Mock
    public PersistencePort persistencePort;

    @Test
    @SneakyThrows
    public void createNodeMapping()
    {
        // Setup
        String originNodeId = "originNodeId";
        String enqueryId = "enqueryId";
        List<String> nodes = List.of("originNodeId", "destinyNodeId1", "destinyNodeId2");

        // Test
        List<NodeMapping> nodeMappingList = nodeMappingUseCase.createNodeMapping(originNodeId, enqueryId, nodes);

        // Assertions
        assertEquals(nodeMappingList.size(), nodes.size() - 1);
        assertTrue(nodeMappingList
            .stream()
            .noneMatch(nodeMapping -> nodeMapping.getNodeId().equals(originNodeId))
        );

        for (NodeMapping nodeMapping : nodeMappingList) {
            assertNotNull(nodeMapping.getEnqueryNodeId());
            assertTrue(nodes.contains(nodeMapping.getNodeId()));
            assertNotNull(nodeMapping.getAlias());
        }
    }

    @Test
    @SneakyThrows
    public void getNode()
    {
        // Setup
        String originNodeId = "originNodeId";

        // Test
        NodeMapping nodeMapping = nodeMappingUseCase.getNode("originNodeId");

        // Assertions
        assertNotNull(nodeMapping.getEnqueryNodeId());
        assertEquals(nodeMapping.getNodeId(), originNodeId);
        assertNotNull(nodeMapping.getAlias());
    }

    @Test
    @SneakyThrows
    public void getProcessId()
    {
        // Setup
        String enqueryNodeId = "originNodeId";

        Mapping expectedMapping = new Mapping();
        expectedMapping.setNodeMappingId(1234);
        expectedMapping.setOriginInstitution("originInstitution");
        expectedMapping.setDestinyInstitution("destinyInstitution");
        expectedMapping.setProcessId("processId");
        expectedMapping.setDestinyMapping(enqueryNodeId);
        when(persistencePort.getProcessId(enqueryNodeId)).thenReturn(expectedMapping);

        // Test
        Mapping actualMapping = nodeMappingUseCase.getProcessId(enqueryNodeId);

        // Assertions
        assertEquals(expectedMapping, actualMapping);
    }

    @Test
    @SneakyThrows
    public void getEnqueryKeyAlias()
    {
        // Setup
        String nodeMappingId = "nodeMappingId";
        boolean isConversationOrigin = true;
        String processType = "EPTE";

        Mapping mapping = new Mapping();
        mapping.setNodeMappingId(1234);
        mapping.setOriginInstitution("originInstitution");
        mapping.setDestinyInstitution("destinyInstitution");
        mapping.setProcessId("processId");
        mapping.setDestinyMapping(nodeMappingId);
        when(persistencePort.getProcessId(nodeMappingId)).thenReturn(mapping);

        // Test
        KeyResponseDTO keyResponseDTO = nodeMappingUseCase.getKeyAlias(
            nodeMappingId, isConversationOrigin, processType
        );

        // Assertions
        assertEquals(keyResponseDTO.getOriginNode(), mapping.getOriginInstitution());
        assertEquals(keyResponseDTO.getKeyNode(), mapping.getDestinyInstitution());
    }

    @Test
    @SneakyThrows
    public void getAlertKeyAlias()
    {
        // Setup
        String nodeMappingId = "nodeMappingId";
        boolean isConversationOrigin = true;
        String processType = "EPTA";

        Mapping mapping = new Mapping();
        mapping.setNodeMappingId(1234);
        mapping.setOriginInstitution("originInstitution");
        mapping.setDestinyInstitution("destinyInstitution");
        mapping.setProcessId("processId");
        mapping.setDestinyMapping(nodeMappingId);
        when(persistencePort.getProcessId(nodeMappingId)).thenReturn(mapping);

        // Test
        KeyResponseDTO keyResponseDTO = nodeMappingUseCase.getKeyAlias(
            nodeMappingId, isConversationOrigin, processType
        );

        // Assertions
        assertEquals(keyResponseDTO.getOriginNode(), mapping.getDestinyInstitution());
        assertEquals(keyResponseDTO.getKeyNode(), mapping.getOriginInstitution());
    }

    @Test
    @SneakyThrows
    public void getKeyAliasNotFound()
    {
        // Setup
        String nodeMappingId = "nodeMappingId";
        boolean isConversationOrigin = false;
        String processType = "EPTE";

        Mapping mapping = new Mapping();
        mapping.setNodeMappingId(1234);
        mapping.setOriginInstitution("originInstitution");
        mapping.setDestinyInstitution("destinyInstitution");
        mapping.setProcessId("processId");
        mapping.setDestinyMapping(nodeMappingId);

        // Test
        ThrowingRunnable runnable
            = () -> nodeMappingUseCase.getKeyAlias(nodeMappingId, isConversationOrigin, processType);

        // Assertions
        assertThrows(NotFoundException.class, runnable);
    }

    @Test
    @SneakyThrows
    public void getKeyAliasValidationException()
    {
        // Setup
        String nodeMappingId = "nodeMappingId";
        boolean isConversationOrigin = false;
        String processType = "as;dlfj";

        Mapping mapping = new Mapping();
        mapping.setNodeMappingId(1234);
        mapping.setOriginInstitution("originInstitution");
        mapping.setDestinyInstitution("destinyInstitution");
        mapping.setProcessId("processId");
        mapping.setDestinyMapping(nodeMappingId);
        when(persistencePort.getProcessId(nodeMappingId)).thenReturn(mapping);

        // Test
        ThrowingRunnable runnable
            = () -> nodeMappingUseCase.getKeyAlias(nodeMappingId, isConversationOrigin, processType);

        // Assertions
        assertThrows(ValidationException.class, runnable);
    }

    @Test
    public void persistNodeMappings()
    {
        Mapping nodeMapping = new Mapping();
        nodeMapping.setOriginInstitution("originInstitution1");
        nodeMapping.setDestinyInstitution("destinyInstitution1");
        nodeMapping.setProcessId("processId1");
        nodeMapping.setDestinyMapping("destinyMapping1");

        List<Mapping> nodeMappings = List.of(
            nodeMapping
        );

        nodeMappingUseCase.persistNodeMappings(nodeMappings);

        ArgumentCaptor<List<Mapping>> nodeMappingsCaptor = ArgumentCaptor.forClass(List.class);
        verify(persistencePort,times(1)).persistNodeMappings(nodeMappingsCaptor.capture());
        Mapping nodeMappingPersistence = nodeMappingsCaptor.getValue().get(0);
        assertEquals(nodeMappingPersistence.getOriginInstitution(), nodeMapping.getOriginInstitution());
        assertEquals(nodeMappingPersistence.getDestinyInstitution(), nodeMapping.getDestinyInstitution());
        assertEquals(nodeMappingPersistence.getProcessId(), nodeMapping.getProcessId());
        assertEquals(nodeMappingPersistence.getDestinyMapping(), nodeMapping.getDestinyMapping());
    }
}
