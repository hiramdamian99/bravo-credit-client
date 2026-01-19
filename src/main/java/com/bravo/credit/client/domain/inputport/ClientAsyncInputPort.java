/*
 * D. R. © Veradat Smart Network, S.A.P.I de C.V., Ciudad de México, 2023
 * VERADAT PROPRIETARY/CONFIDENCIAL. Use is subject to license terms.
 *
 * Project: veradat-node-manager
 * File: NodeMappingAsyncInputPort.java
 */

package com.bravo.credit.client.domain.inputport;



import com.bravo.credit.client.domain.model.Client;

/**
 * The interface Node mapping async input port.
 * @Author: Hiram Lopez Damian
 * @LastContributor: Hiram Lopez Damian
 * @Created At: 05/03/2025
 * @Updated At: 13/01/2026
 */
public interface ClientAsyncInputPort {



    /**
     * Gets process id.
     *
     * @param enqueryNodeId the enquery node id
     *
     * @return the process id
     */
    Client getByDestinyMapping(String enqueryNodeId) ;



    void createdClient(Client client) throws Exception;


    /**
     * Persist node mappings.
     *
     * @param nodeMappings the node mappings
     */
    void persistNodeMappings(Client nodeMappings) throws Exception;

}
