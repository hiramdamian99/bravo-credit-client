/*
 * D. R. © Veradat Smart Network, S.A.P.I de C.V., Ciudad de México, 2023
 * VERADAT PROPRIETARY/CONFIDENCIAL. Use is subject to license terms.
 *
 * Project: veradat-node-manager
 * Module: adapter-persistence-spring-data-jpa
 * File: NodeMappingEntity.java
 */

package com.bravo.credit.client.infrastructure.percistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.Instant;

/**
 * The type Node mapping entity.
 */
@Data
@Entity
@Table(
    name = "tr_client",
    schema = "control"
)
public class ClientEntity
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "process_id")
    private String processId;

    @Column(name = "mount_income")
    private String mountIncome;

    @Column(name = "destiny_institution")
    private String destinyInstitution;

    @Column(name = "country")
    private String country;

    @Column(name = "destiny_mapping")
    private String amount;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;


}
