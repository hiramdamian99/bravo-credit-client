/*
 * D. R. © Veradat Smart Network, S.A.P.I de C.V., Ciudad de México, 2023
 * VERADAT PROPRIETARY/CONFIDENCIAL. Use is subject to license terms.
 *
 * Project: veradat-node-manager
 * Module: domain
 * File: NodeMapping.java
 */

package com.veradat.vdt.node.manager.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * The type Node mapping.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NodeMapping implements Serializable
{
    private String enqueryNodeId;

    private String nodeId;

    private String alias;
}
