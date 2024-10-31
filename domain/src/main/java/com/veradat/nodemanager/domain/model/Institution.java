/*
 * D. R. © Veradat Smart Network, S.A.P.I de C.V., Ciudad de México, 2023
 * VERADAT PROPRIETARY/CONFIDENCIAL. Use is subject to license terms.
 *
 * Project: veradat-node-manager
 * Module: domain
 * File: Institution.java
 */

package com.veradat.nodemanager.domain.model;

import lombok.Data;

import java.io.Serializable;

/**
 * The type Institution.
 */
@Data
public class Institution implements Serializable
{

    private String institutionType;

    private String code;

    private String alias;

    private String currentWeightPl;

    private String currentWeightWl;

    private double currentPrivateListCount;

    private double currentWatchListCount;

    private String endpoint;
}
