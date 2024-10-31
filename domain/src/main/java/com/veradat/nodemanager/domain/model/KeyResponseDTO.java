/*
 * D. R. © Veradat Smart Network, S.A.P.I de C.V., Ciudad de México, 2023
 * VERADAT PROPRIETARY/CONFIDENCIAL. Use is subject to license terms.
 *
 * Project: veradat-node-manager
 * Module: domain
 * File: KeyResponseDTO.java
 */

package com.veradat.nodemanager.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * The type Key response dto.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeyResponseDTO implements Serializable
{

    private String originNode;

    private String keyNode;
}
