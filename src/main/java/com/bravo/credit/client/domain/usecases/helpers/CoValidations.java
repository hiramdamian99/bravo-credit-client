package com.bravo.credit.client.domain.usecases.helpers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
@Service
public class CoValidations {

    private final Logger log = LoggerFactory.getLogger(CoValidations.class);

    /**
     * Cédula de ciudadanía (Colombia) - validación por FORMATO.
     * Regla práctica: solo dígitos, 6 a 11 caracteres.
     *
     * Notas:
     * - No existe un "regex oficial con checksum" ampliamente estandarizado para CC.
     * - Si tu negocio requiere una longitud específica (p.ej. 10), ajusta el rango.
     */
    public static final String CO_CC_REGEX = "^(?!(?:0)\\1+$)\\d{6,11}$";


    private static final Map<String, Pattern> patterns = new ConcurrentHashMap<>();

    public static boolean matches(String value, String regex) {
        if (value == null) return false;
        return patterns.computeIfAbsent(regex, Pattern::compile)
                .matcher(value.trim())
                .matches();
    }

    public void validateCc(String cc) throws Exception {
        log.debug("Validating Cédula de Ciudadanía: {}", cc);
        if (!matches(cc, CO_CC_REGEX)) {
            throw new Exception("La cédula de ciudadanía no es válida (formato).");
        }
    }


    public void validateAmountMonthIncomeColombia(Integer monthlyIncome, Integer amount) throws Exception {

        if (monthlyIncome < amount) {
            throw new Exception("El ingreso mensual no puede ser menor al monto solicitado.");
        }

        if (monthlyIncome < amount * 0.3) {
            throw new Exception("El ingreso mensual debe ser al menos el 30% del monto solicitado.");
        }
    }



}
