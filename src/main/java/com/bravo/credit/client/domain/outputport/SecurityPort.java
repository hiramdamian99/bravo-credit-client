/*
 * D. R. © Hiram Solutions de C.V., Ciudad de México, 2026
 * CONFIDENTIAL Use is subject to license terms.
 *
 * Project: bravo-credit-client
 * File: SecurityPort.java
 */


package com.bravo.credit.client.domain.outputport;


public interface SecurityPort {

    /**
     * Get user identifier
     * @return User identifier
     */
    String getCreatedBy();

}