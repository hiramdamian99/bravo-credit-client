/*
 * D. R. © Veradat Smart Network, S.A.P.I de C.V., Ciudad de México, 2023
 * VERADAT PROPRIETARY/CONFIDENCIAL. Use is subject to license terms.
 *
 * Project: veradat-node-manager
 * Module: domain
 * File: KeyName.java
 */

package com.veradat.nodemanager.domain.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * The type Key name.
 */
@Data
public class KeyName implements Serializable
{

    private Integer id;

    private String nodeId;

    private String publicKeyRoute;

    private String publicKeyAlias;

    private String isActive;

    private Date activeStartDate;

    private Date activeEndDate;
}
