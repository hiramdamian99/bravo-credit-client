/*
 * D. R. © Veradat Smart Network, S.A.P.I de C.V., Ciudad de México, 2023
 *  VERADAT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * Project: veradat-node-manager
 * File: AsyncInputAdapter
 * Author: Hiram Lopez Damian
 * Created At: 12/09/2023
 * Updated At: 12/03/2025
 * Description: This class persist node routings information
 */


package com.veradat.vdt.node.manager.application.async;

import com.veradat.commons.exception.VeradatException;
import com.veradat.commons.exception.utils.IdentifierManager;
import com.veradat.lib.messages.general.annotation.VeradatAsyncConsumer;
import com.veradat.lib.messages.general.annotation.VeradatListener;
import com.veradat.vdt.node.manager.domain.inputport.NodeMappingAsyncInputPort;
import com.veradat.vdt.node.manager.domain.model.match.NodeMappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.veradat.lib.security.model.AuditScope.AUDIT_ALL;


/**
 * This class persist node routings information
 */
@Service
@VeradatAsyncConsumer
public class AsyncInputAdapter {

    static {
        IdentifierManager.registerClassIdentifier(AsyncInputAdapter.class, "AIA");
    }

    private final NodeMappingAsyncInputPort nodeMappingUseCase;


    @Autowired
    public AsyncInputAdapter(NodeMappingAsyncInputPort nodeMappingUseCase) {
        this.nodeMappingUseCase = nodeMappingUseCase;
    }

    /**
     * Persiste la informacion de un proceso de cotejo programado
     * @param nodeMappings
     */
    @VeradatListener(
        queues = "${veradat.input.queues.persist-node-routings}",
        permission=  "PE",
        auditScope  = AUDIT_ALL
    )
    public void persistScheduledMatchProcess(NodeMappings nodeMappings) throws VeradatException {
        nodeMappingUseCase.persistNodeMappings(nodeMappings.getMappings());
    }
}
