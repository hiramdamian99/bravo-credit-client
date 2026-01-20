/*
 * D. R. © Hiram Solutions de C.V., Ciudad de México, 2026
 * CONFIDENTIAL Use is subject to license terms.
 *
 * Project: bravo-credit-client
 * File: Client.java
 */


package com.bravo.credit.client.domain.model;




import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.time.OffsetDateTime;

/**
 * The type Client.
 */
@Data
@AllArgsConstructor
public class Client {

    private final Long processId;
    private final String identifier;
    private final Integer monthlyIncome;
    private final Integer amount;
    private final String country;
    private final Instant createdAt;
    private final Instant updatedAt;
    private final String createdBy;
    private final String updatedBy;

    public Client(
            Long processId,
            String identifier,
            Integer monthlyIncome,
            Integer amount,
            String country,
            OffsetDateTime createdAt,
            OffsetDateTime updatedAt,
            String createdBy,
            String updatedBy
    ) {
        this.processId = processId;
        this.identifier = identifier;
        this.monthlyIncome = monthlyIncome;
        this.amount = amount;
        this.country = country;
        this.createdAt = createdAt != null ? createdAt.toInstant() : null;
        this.updatedAt = updatedAt != null ? updatedAt.toInstant() : null;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }
}
