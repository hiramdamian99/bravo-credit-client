/*
 * D. R. © Hiram Solutions de C.V., Ciudad de México, 2026
 * CONFIDENTIAL Use is subject to license terms.
 *
 * Project: bravo-credit-client
 * File: ClientRepository.java
 */

package com.bravo.credit.client.infrastructure.percistence.repository;

import com.bravo.credit.client.domain.model.Client;
import com.bravo.credit.client.infrastructure.percistence.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

/**
 * The interface Node mapping repository.
 */
public interface ClientRepository extends JpaRepository<ClientEntity, Integer>


{

    @Modifying
    @Transactional
    @Query(value = """
    INSERT INTO control.tr_client (
        identifier,
        monthly_income,
        amount,
        country,
        created_at,
        updated_at,
        created_by,
        updated_by
    )
    VALUES (
        :identifier,
        :monthlyIncome,
        :amount,
        :country,
        :createdAt,
        :updatedAt,
        :createdBy,
        :updatedBy
    )
""", nativeQuery = true)
    void createdClient(
            @Param("identifier") String identifier,
            @Param("monthlyIncome") Integer monthlyIncome,
            @Param("amount") Integer amount,
            @Param("country") String country,
            @Param("createdAt") Instant createdAt,
            @Param("updatedAt") Instant updatedAt,
            @Param("createdBy") String createdBy,
            @Param("updatedBy") String updatedBy
    );


    @Query(nativeQuery = true, name = "findClient")
    List<Client> findClient(
            @Param("processId") Long processId,
            @Param("identifier") String identifier,
            @Param("identifierLike") String identifierLike,
            @Param("monthlyIncome") Integer monthlyIncome,
            @Param("monthlyIncomeMin") Integer monthlyIncomeMin,
            @Param("monthlyIncomeMax") Integer monthlyIncomeMax,
            @Param("amount") Integer amount,
            @Param("amountMin") Integer amountMin,
            @Param("amountMax") Integer amountMax,
            @Param("country") String country,
            @Param("createdFrom") Instant createdFrom,
            @Param("createdTo") Instant createdTo,
            @Param("updatedFrom") Instant updatedFrom,
            @Param("updatedTo") Instant updatedTo,
            @Param("createdBy") String createdBy,
            @Param("updatedBy") String updatedBy
    );
}
