package com.bravo.credit.client.infrastructure.security;


import com.bravo.credit.client.domain.outputport.SecurityPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.rmi.server.UID;

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


    /**
     * Get user identifier
     * @return User identifier
     */
    public String getCreatedBy(){
       return new UID().toString();
    }

}