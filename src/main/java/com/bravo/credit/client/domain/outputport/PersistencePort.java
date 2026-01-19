/*
 * D. R. © Veradat Smart Network, S.A.P.I de C.V., Ciudad de México, 2023
 * VERADAT PROPRIETARY/CONFIDENCIAL. Use is subject to license terms.
 *
 * Project: veradat-node-manager
 * Module: domain
 * File: PersistencePort.java
 */

package com.bravo.credit.client.domain.outputport;

import com.bravo.credit.client.domain.model.Client;

import java.util.List;

/**
 *  The interface Persistence port.
 * @Author: Hiram Lopez Damian
 * @LastContributor: Hiram Lopez Damian
 * @Created At: 05/03/2025
 * @Updated At: 13/01/2026
 */
public interface PersistencePort
{


    /**
     * Gets process id.
     *
     * @param enqueryNodeId the enquery node id
     *
     * @return the process id
     */
    List<Client> getByDestinyMapping(Client client) ;

    void createdClient(Client client) ;

}
