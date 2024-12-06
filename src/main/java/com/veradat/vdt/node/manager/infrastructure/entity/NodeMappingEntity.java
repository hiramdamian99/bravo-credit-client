/*
 * D. R. © Veradat Smart Network, S.A.P.I de C.V., Ciudad de México, 2023
 * VERADAT PROPRIETARY/CONFIDENCIAL. Use is subject to license terms.
 *
 * Project: veradat-node-manager
 * Module: adapter-persistence-spring-data-jpa
 * File: NodeMappingEntity.java
 */

package com.veradat.vdt.node.manager.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * The type Node mapping entity.
 */
@Data
@Entity
@Table(
    name = "tr_node_mappings",
    schema = "control"
)
public class NodeMappingEntity
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "node_mapping_id")
    private Integer nodeMappingId;

    @Column(name = "origin_institution")
    private String originInstitution;

    @Column(name = "destiny_institution")
    private String destinyInstitution;

    @Column(name = "process_id")
    private String processId;

    @Column(name = "destiny_mapping")
    private String destinyMapping;
}
