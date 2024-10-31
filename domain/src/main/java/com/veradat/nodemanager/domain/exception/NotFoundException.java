package com.veradat.nodemanager.domain.exception;

import com.veradat.commons.exception.CustomValidationException;

public class NotFoundException extends CustomValidationException {
    public NotFoundException(String message, int exceptionId) {
        super(message, exceptionId);
    }
}
