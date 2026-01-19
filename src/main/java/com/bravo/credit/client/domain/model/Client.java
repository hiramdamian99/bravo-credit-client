package com.bravo.credit.client.domain.model;




import lombok.Data;
import java.time.LocalDate;

/**
 * The type Client.
 */
@Data
public class Client {

    private String identifier;

    private  String monthlyIncome;

    private String country;

    private String amount;

    private LocalDate date;


}
