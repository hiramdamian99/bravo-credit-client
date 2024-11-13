/*
 * D. R. © Veradat Smart Network, S.A.P.I de C.V., Ciudad de México, 2023
 * VERADAT PROPRIETARY/CONFIDENCIAL. Use is subject to license terms.
 *
 * Project: veradat-node-manager
 * Module: adapter-persistence-spring-data-jpa
 * File: InstitutionEntity.java
 */

package com.veradat.node.manager.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * The type Institution entity.
 */
@Data
@Entity
@Table(
        name = "cat_cfg_institutions",
        schema = "core"
)
public class InstitutionEntity
{
    @Id
    @Column(name = "institution_id")
    private Integer institutionId;

    @Column(name = "institution_type")
    private String institutionType;

    @Column(name = "code")
    private String code;

    @Column(name = "current_weight_pl")
    private String currentWeightPl;

    @Column(name = "current_weight_wl")
    private String currentWeightWl;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "is_online")
    private Boolean isOnline;

    @Column(name = "current_private_list_count")
    private Double currentPrivateListCount;

    @Column(name = "current_watch_list_count")
    private Double currentWatchListCount;

    @Column(name = "endpoint")
    private String endpoint;

    @Column(name = "blocked_type")
    private String blockedType;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "last_blocked_date")
    private Date lastBlockedDate;

    @Column(name = "last_healthy_date")
    private Date lastHeatlthyDate;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "version_number")
    private String versionNumber;

    @Column(name = "is_active")
    private Character isActive;
}
