/*
 * D. R. © Hiram Solutions de C.V., Ciudad de México, 2026
 * CONFIDENTIAL Use is subject to license terms.
 *
 * Project: bravo-credit-client
 * File: MxValidations.java
 */


package com.bravo.credit.client.domain.usecases.helpers;


import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

@Service
public class MxValidations {

    public static final String CURP_REGEX = "^([A-Z]{4}\\d{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[12]\\d|3[01])[HM](?:AS|B[CS]|C[CLMSH]|D[FG]|G[TR]|HG|JC|M[CNS]|N[ETL]|OC|PL|Q[TR]|S[PLR]|T[CSL]|VZ|YN|ZS)[B-DF-HJ-NP-TV-Z]{3}[A-Z\\d])(\\d)$";


    private static final Map<String, Pattern> patterns = new ConcurrentHashMap();

    public static boolean matches(String value, String regex) {
        return ((Pattern)patterns.computeIfAbsent(regex, Pattern::compile)).matcher(value).matches();
    }

    public void validateCurp(String curp) throws  Exception {
        if ( !matches(curp, CURP_REGEX)) {
            throw new Exception("El CURP no es válido.");
        }
    }



}
