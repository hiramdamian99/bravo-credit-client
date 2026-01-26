package com.bravo.credit.client.domain.outputport;

import com.bravo.credit.client.domain.model.BankInformation;
import com.bravo.credit.client.domain.model.Client;

public interface BankPort {


    BankInformation bancolombiaInformation(Client client);

    BankInformation bbvaInformation(Client client);
}
