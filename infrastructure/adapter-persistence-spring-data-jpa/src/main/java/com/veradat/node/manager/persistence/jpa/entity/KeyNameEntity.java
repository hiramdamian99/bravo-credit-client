/*
 * D. R. © Veradat Smart Network, S.A.P.I de C.V., Ciudad de México, 2023
 * VERADAT PROPRIETARY/CONFIDENCIAL. Use is subject to license terms.
 *
 * Project: veradat-node-manager
 * Module: adapter-persistence-spring-data-jpa
 * File: KeyNameEntity.java
 */

package com.veradat.node.manager.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

/**
 * The type Key name entity.
 */
@Data
@Entity
@Table(
        name = "cat_cfg_public_key_names",
        schema = "core"
)

public class KeyNameEntity
{

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "node_id")
    private String nodeId;

    @Column(name = "public_key_route")
    private String publicKeyRoute;

    @Column(name = "public_key_alias")
    private String publicKeyAlias;

    @Column(name = "is_active")
    private String isActive;

    @Column(name = "active_start_date")
    private Date activeStartDate;

    @Column(name = "active_end_date")
    private Date activeEndDate;
}
