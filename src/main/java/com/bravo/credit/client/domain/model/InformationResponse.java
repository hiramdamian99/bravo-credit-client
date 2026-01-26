package com.bravo.credit.client.domain.model;


import lombok.Data;

import java.time.Instant;

@Data
public class InformationResponse {


    private String accountId;

    private Boolean approved;

    private String statusRequest;

    private String message;

    private Instant createdAt;
}
