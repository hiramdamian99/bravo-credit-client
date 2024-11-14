/*
 * D. R. © Veradat Smart Network, S.A.P.I de C.V., Ciudad de México, 2023
 * VERADAT PROPRIETARY/CONFIDENCIAL. Use is subject to license terms.
 *
 * Project: veradat-node-manager
 * Module: domain
 * File: Mapping.java
 */

package com.veradat.vdt.node.manager.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * The type Mapping.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mapping implements Serializable
{

    private Integer nodeMappingId;

    private String originInstitution;

    private String destinyInstitution;

    private String processId;

    private String destinyMapping;
}
