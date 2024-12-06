/*
 * D. R. © Veradat Smart Network, S.A.P.I de C.V., Ciudad de México, 2023
 * VERADAT PROPRIETARY/CONFIDENCIAL. Use is subject to license terms.
 *
 * Project: veradat-node-manager
 * Module: domain
 * File: NodeMappingCommand.java
 */

package com.veradat.vdt.node.manager.domain.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * The type Node mapping command.
 */
@Data
public class NodeMappingCommand implements Serializable
{
    @NotNull
    private String originNodeId;

    @NotNull
    private String processId;

    @NotNull
    private List<String> nodes;
}
