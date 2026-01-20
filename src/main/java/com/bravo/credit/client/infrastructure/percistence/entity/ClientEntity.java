/*
 * D. R. © Hiram Solutions de C.V., Ciudad de México, 2026
 * CONFIDENTIAL Use is subject to license terms.
 *
 * Project: bravo-credit-client
 * File: ClientEntity.java
 */

package com.bravo.credit.client.infrastructure.percistence.entity;

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
import java.time.OffsetDateTime;


@SqlResultSetMapping(
        name = "findClientResult",
        classes = @ConstructorResult(
                targetClass = com.bravo.credit.client.domain.model.Client.class,
                columns = {
                        @ColumnResult(name = "process_id", type = Long.class),
                        @ColumnResult(name = "identifier", type = String.class),
                        @ColumnResult(name = "monthly_income", type = Integer.class),
                        @ColumnResult(name = "amount", type = Integer.class),
                        @ColumnResult(name = "country", type = String.class),
                        @ColumnResult(name = "created_at", type = OffsetDateTime.class),
                        @ColumnResult(name = "updated_at", type = OffsetDateTime.class),
                        @ColumnResult(name = "created_by", type = String.class),
                        @ColumnResult(name = "updated_by", type = String.class)
                }
        )
)



@NamedNativeQuery(
        name = "findClient",
        query =
                """
             SELECT *
         FROM control.tr_client tc
         WHERE
             ( :processId IS NULL OR tc.process_id = :processId )  
             AND ( :identifier IS NULL OR tc.identifier = :identifier )
             AND ( :identifierLike IS NULL OR tc.identifier ILIKE CONCAT('%', :identifierLike, '%') )
             AND ( :monthlyIncome IS NULL OR tc.monthly_income = :monthlyIncome )
             AND ( :monthlyIncomeMin IS NULL OR tc.monthly_income >= :monthlyIncomeMin )
             AND ( :monthlyIncomeMax IS NULL OR tc.monthly_income <= :monthlyIncomeMax )
             AND ( :amount IS NULL OR tc.amount = :amount )
             AND ( :amountMin IS NULL OR tc.amount >= :amountMin )
             AND ( :amountMax IS NULL OR tc.amount <= :amountMax )
             AND ( :country IS NULL OR tc.country = :country )
           AND ( CAST(:createdFrom AS TIMESTAMPTZ) IS NULL OR tc.created_at >= CAST(:createdFrom AS TIMESTAMPTZ) )
           AND ( CAST(:createdTo   AS TIMESTAMPTZ) IS NULL OR tc.created_at <= CAST(:createdTo   AS TIMESTAMPTZ) )
           AND ( CAST(:updatedFrom AS TIMESTAMPTZ) IS NULL OR tc.updated_at >= CAST(:updatedFrom AS TIMESTAMPTZ) )
           AND ( CAST(:updatedTo   AS TIMESTAMPTZ) IS NULL OR tc.updated_at <= CAST(:updatedTo   AS TIMESTAMPTZ) )
             AND ( :createdBy IS NULL OR tc.created_by = :createdBy )
             AND ( :updatedBy IS NULL OR tc.updated_by = :updatedBy )
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

    @Column(name = "identifier")
    private  String identifier;

    @Column(name = "monthly_income")
    private Integer monthlyIncome;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "country")
    private String country;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;


}
