package com.bravo.credit.client.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientResponse {

    private  Long processId;
    private  String identifier;
    private  Integer monthlyIncome;
    private  Integer amount;
    private  String country;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private  String createdBy;
    private  String updatedBy;
}
