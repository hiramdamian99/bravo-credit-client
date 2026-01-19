/*
 * D. R. © Veradat Smart Network, S.A.P.I de C.V., Ciudad de México, 2023
 * VERADAT PROPRIETARY/CONFIDENCIAL. Use is subject to license terms.
 *
 * Project: veradat-node-manager
 * Module: adapter-persistence-spring-data-jpa
 * File: NodeMappingEntity.java
 */

package com.bravo.credit.client.infrastructure.percistence.entity;

import com.bravo.credit.client.domain.model.Client;
import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.Instant;



@SqlResultSetMapping(
        name = "findClientResult",
        classes =
        @ConstructorResult(
                targetClass = Client.class,
                columns = {
                        @ColumnResult(name = "process_id", type = String.class),
                        @ColumnResult(name = "mount_income", type =  String.class),
                        @ColumnResult(name = "destiny_institution", type =  String.class),
                        @ColumnResult(name = "country", type =  String.class),
                        @ColumnResult(name = "destiny_mapping", type =  String.class)
                }))



@NamedNativeQuery(
        name = "findClient",
        query =
                """
                        select * from tr_client tc
    WHERE   ( :mount_income   IS NULL   OR mount_income     = :mount_income )
    and ( :country   IS NULL   OR country     = :country )
          AND (
                  CAST(:startDate AS TIMESTAMP) IS NULL
               OR CAST(:endDate   AS TIMESTAMP) IS NULL
               OR created_at BETWEEN
                      CAST(:startDate AS TIMESTAMP)
                  AND CAST(:endDate   AS TIMESTAMP)
              );
				""",
        resultSetMapping = "findClientResult")





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
