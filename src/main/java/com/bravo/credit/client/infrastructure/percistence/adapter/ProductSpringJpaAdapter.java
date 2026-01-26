/*
 * D. R. © Hiram Solutions de C.V., Ciudad de México, 2026
 * CONFIDENTIAL Use is subject to license terms.
 *
 * Project: bravo-credit-client
 * File: ProductSpringJpaAdapter.java
 */
package com.bravo.credit.client.infrastructure.percistence.adapter;


import com.bravo.credit.client.domain.model.Client;
import com.bravo.credit.client.domain.model.ClientRequest;
import com.bravo.credit.client.domain.model.ClientResponse;
import com.bravo.credit.client.domain.outputport.PersistencePort;
import com.bravo.credit.client.infrastructure.percistence.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;

/**
 * adapter for node mapping persistence
 * @Author: Hiram Lopez Damian
 * @LastContributor: Hiram Lopez Damian
 * @Created At: 05/03/2025
 * @Updated At: 13/01/2026
 */
@Service
public class ProductSpringJpaAdapter implements PersistencePort {


    private final ClientRepository nodeMappingRepository;

    @Autowired
    public ProductSpringJpaAdapter(
        ClientRepository nodeMappingRepository
    ){
        this.nodeMappingRepository = nodeMappingRepository;
    }


    /**
     * Gets process id.
     *
     * @param client the client
     *
     */
    public void createdClient (Client client){

        try{
        nodeMappingRepository.createdClient(
                client.getIdentifier(),
                client.getMonthlyIncome(),
                client.getAmount(),
                client.getCountry(),
                Instant.now(),
                Instant.now(),
                "SYSTEM",
                "SYSTEM");

        } catch (DataIntegrityViolationException e){
            throw new RuntimeException(
                    "Error creating client: " + e.getMessage()
            );
        }catch (Exception e){
            throw new RuntimeException(
                    "Error creating client: " + e.getMessage()
            );
        }


    }

    /**
     * Gets process id.
     *
     * @param req request parameters
     *
     * @return the process id
     */
    public List<ClientResponse> getClientData(ClientRequest req) {

        try {
            return nodeMappingRepository.findClient(
                    req.getProcessId(),
                    req.getIdentifier(),
                    req.getIdentifierLike(),
                    req.getMonthlyIncome(),
                    req.getMonthlyIncomeMin(),
                    req.getMonthlyIncomeMax(),
                    req.getAmount(),
                    req.getAmountMin(),
                    req.getAmountMax(),
                    req.getCountry(),
                    req.getCreatedFrom(),
                    req.getCreatedTo(),
                    req.getUpdatedFrom(),
                    req.getUpdatedTo(),
                    req.getCreatedBy(),
                    req.getUpdatedBy()
            );

        }  catch (QueryTimeoutException e){
        throw new RuntimeException(
                "Error creating client: " + e.getMessage()
        );
        } catch (Exception e){
        throw new RuntimeException(
                "Error creating client: " + e.getMessage()
        );

    }
    }




}
