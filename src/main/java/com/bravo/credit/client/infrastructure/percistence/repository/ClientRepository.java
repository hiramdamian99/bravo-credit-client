/*
 * D. R. © Veradat Smart Network, S.A.P.I de C.V., Ciudad de México, 2023
 * VERADAT PROPRIETARY/CONFIDENCIAL. Use is subject to license terms.
 *
 * Project: veradat-node-manager
 * Module: adapter-persistence-spring-data-jpa
 * File: NodeMappingRepository.java
 */

package com.bravo.credit.client.infrastructure.percistence.repository;

import com.bravo.credit.client.infrastructure.percistence.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

/**
 * The interface Node mapping repository.
 */
public interface ClientRepository extends JpaRepository<ClientEntity, Integer>


{

    @Modifying
    @Transactional
    @Query(value = """
    INSERT INTO control.tr_client (
        mount_income,
        destiny_institution,
        country,
        destiny_mapping,
        created_at,
        updated_at,
        created_by,
        updated_by
    )
    VALUES (
        :mountIncome,
        :destinyInstitution,
        :country,
        :amount,
        :createdAt,
        :updatedAt,
        :createdBy,
        :updatedBy
    )
""", nativeQuery = true)
    void createdClient(
            @Param("mountIncome") String mountIncome,
            @Param("destinyInstitution") String destinyInstitution,
            @Param("country") String country,
            @Param("amount") String amount,
            @Param("createdAt") Instant createdAt,
            @Param("updatedAt") Instant updatedAt,
            @Param("createdBy") String createdBy,
            @Param("updatedBy") String updatedBy
    );

}
