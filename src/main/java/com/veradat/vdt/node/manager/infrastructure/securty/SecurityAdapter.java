package com.veradat.vdt.node.manager.infrastructure.securty;

import com.veradat.lib.security.DetailsService;
import com.veradat.vdt.node.manager.domain.outputport.SecurityPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Security details Adapter
 * @author Hiram Lopez Damian
 * @lastContributor Hiram Lopez Damian
 * @createdAt 12/01/2026
 * @updatedAt 12/01/2026
 */
@Service
@RequiredArgsConstructor
public class SecurityAdapter implements SecurityPort {

    private final DetailsService detailsService;

    /**
     * Get user identifier
     * @return User identifier
     */
    public String getCreatedBy(){
        return detailsService.getUserIdentifier() != null ? detailsService.getUserIdentifier() : detailsService.getNodeApiKey();
    }

}