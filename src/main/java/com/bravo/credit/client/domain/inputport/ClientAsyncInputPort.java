/*
 * D. R. © Hiram Solutions de C.V., Ciudad de México, 2026
 * CONFIDENTIAL Use is subject to license terms.
 *
 * Project: bravo-credit-client
 * File: ClientAsyncInputPort.java
 */
package com.bravo.credit.client.domain.inputport;



import com.bravo.credit.client.domain.model.Client;
import com.bravo.credit.client.domain.model.ClientRequest;
import com.bravo.credit.client.domain.model.ClientResponse;

import java.util.List;

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



    void createdClient(Client client) throws Exception;


    /**
     * Persist node mappings.
     *
     * @param nodeMappings the node mappings
     */
    List<ClientResponse> getClientData(ClientRequest nodeMappings) throws Exception;

}
