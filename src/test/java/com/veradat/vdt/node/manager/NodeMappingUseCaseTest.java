/*
 * D. R. © Veradat Smart Network, S.A.P.I de C.V., Ciudad de México, 2023
 * VERADAT PROPRIETARY/CONFIDENCIAL. Use is subject to license terms.
 *
 * Project: veradat-node-manager
 * Module: domain
 * File: NodeMappingUseCaseTest.java
 */

package com.veradat.vdt.node.manager;


import com.veradat.commons.exception.VeradatException;
import com.veradat.vdt.node.manager.domain.model.Mapping;
import com.veradat.vdt.node.manager.domain.outputport.PersistencePort;
import com.veradat.vdt.node.manager.domain.usecases.NodeMappingUseCase;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Test for NodeMappingUseCase
 * @Author: Hiram Lopez Damian
 * @LastContributor: Hiram Lopez Damian
 * @Created At: 05/03/2025
 * @Updated At: 13/01/2026
 */
@RunWith(MockitoJUnitRunner.class)
public class NodeMappingUseCaseTest
{
    @InjectMocks
    public NodeMappingUseCase nodeMappingUseCase;

    @Mock
    public PersistencePort persistencePort;




    @Test
    @SneakyThrows
    public void getProcessId() {

        String enqueryNodeId = "originNodeId";

        Mapping expectedMapping = new Mapping();
        expectedMapping.setNodeMappingId(1234);
        expectedMapping.setOriginInstitution("originInstitution");
        expectedMapping.setDestinyInstitution("destinyInstitution");
        expectedMapping.setProcessId("processId");
        expectedMapping.setDestinyMapping(enqueryNodeId);
        when(persistencePort.getByDestinyMapping(enqueryNodeId)).thenReturn(expectedMapping);

        Mapping actualMapping = nodeMappingUseCase.getByDestinyMapping(enqueryNodeId);

        assertEquals(expectedMapping, actualMapping);
    }


    @Test
    public void testPersistNodeMappings() throws VeradatException {
        // Arrange
        Mapping mapping1 = new Mapping(1, "InstA", "InstB", "Proc1", "Dest1");
        Mapping mapping2 = new Mapping(2, "InstC", "InstD", "Proc2", "Dest2");
        List<Mapping> nodeMappings = Arrays.asList(mapping1, mapping2);

        // Act
        nodeMappingUseCase.persistNodeMappings(nodeMappings);

        // Assert
        verify(persistencePort, times(1)).persistNodeMappings(nodeMappings);
    }

    @Test
    public void testPersistNodeMappingsContinue() throws VeradatException {
        Mapping mapping = new Mapping(1, "InstA", "40-012", "123456765432345", "123456787654");
        List<Mapping> nodeMappings = List.of(mapping);

        Mapping existing = new Mapping(99, "InstX", "40-013", "anyProc", "123456787654");

        NodeMappingUseCase spyUseCase = Mockito.spy(nodeMappingUseCase);

        doReturn(existing).when(spyUseCase).getByDestinyMapping("123456787654");

        spyUseCase.persistNodeMappings(nodeMappings);

        verify(spyUseCase, times(1)).getByDestinyMapping("123456787654");
        verify(persistencePort, times(1))
                .persistNodeMappings(argThat(List::isEmpty));
    }




}
