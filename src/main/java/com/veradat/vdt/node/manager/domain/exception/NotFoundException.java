
package com.veradat.vdt.node.manager.domain.exception;



import com.veradat.commons.exception.CustomValidationException;

import java.util.Map;

public class NotFoundException extends CustomValidationException {
    public NotFoundException(String message, int exceptionId, Map<String, Object> businessObjects) {
        super(message, exceptionId, businessObjects);
    }
}
