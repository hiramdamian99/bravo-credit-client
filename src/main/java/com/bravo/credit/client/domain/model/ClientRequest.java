/*
 * D. R. © Hiram Solutions de C.V., Ciudad de México, 2026
 * CONFIDENTIAL Use is subject to license terms.
 *
 * Project: bravo-credit-client
 * File: ClientRequest.java
 */

package com.bravo.credit.client.domain.model;


import lombok.Data;

import java.time.Instant;

@Data
public class ClientRequest {

    private Long processId;

    private String identifier;
    private String identifierLike;

    private Integer monthlyIncome;
    private Integer monthlyIncomeMin;
    private Integer monthlyIncomeMax;

    private Integer amount;
    private Integer amountMin;
    private Integer amountMax;

    private String country;

    private Instant createdFrom;
    private Instant createdTo;

    private Instant updatedFrom;
    private Instant updatedTo;

    private String createdBy;
    private String updatedBy;
}
