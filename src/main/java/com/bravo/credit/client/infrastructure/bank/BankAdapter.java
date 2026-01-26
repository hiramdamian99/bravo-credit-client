package com.bravo.credit.client.infrastructure.bank;

import com.bravo.credit.client.domain.model.BankInformation;
import com.bravo.credit.client.domain.model.Client;
import com.bravo.credit.client.domain.outputport.BankPort;
import org.springframework.stereotype.Service;

@Service
public class BankAdapter implements BankPort {


    @Override
    public BankInformation bancolombiaInformation(Client client) {

        BankInformation bankInformation = new BankInformation();
        bankInformation.setAccountId("5534123443126781");
        bankInformation.setApproved(true);
        return bankInformation;
    }

    @Override
    public BankInformation bbvaInformation(Client client) {

        BankInformation bankInformation = new BankInformation();
        bankInformation.setAccountId("5534123443126789");
        bankInformation.setApproved(true);
        return bankInformation;
    }



}
