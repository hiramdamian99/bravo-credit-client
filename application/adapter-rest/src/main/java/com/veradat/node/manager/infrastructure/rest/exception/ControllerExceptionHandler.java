/*
 * D. R. © Veradat Smart Network, S.A.P.I de C.V., Ciudad de México, 2023
 * VERADAT PROPRIETARY/CONFIDENCIAL. Use is subject to license terms.
 *
 * Project: veradat-node-manager
 * Module: adapter-rest
 * File: ControllerExceptionHandler.java
 */

/**
 *
 */
package com.veradat.node.manager.infrastructure.rest.exception;

import com.veradat.commons.exception.utils.VeradatErrorMessage;
import com.veradat.commons.exception.utils.VeradatExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * The type Controller exception handler.
 *
 * @author veradat
 */
@RestControllerAdvice
public class ControllerExceptionHandler
{

    private final VeradatExceptionUtils veradatExceptionUtils;

    @Autowired
    public ControllerExceptionHandler(VeradatExceptionUtils veradatExceptionUtils)
    {
        this.veradatExceptionUtils = veradatExceptionUtils;
    }

    /**
     * Handle http media type not acceptable response entity.
     *
     * @param ex the ex
     *
     * @return the response entity
     */
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    protected ResponseEntity<VeradatErrorMessage> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex)
    {
        VeradatErrorMessage em = veradatExceptionUtils.buildErrorMessage(ex, "El tipo de medio no es admitido");
        return new ResponseEntity<>(em, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    /**
     * Handle http media type not supported response entity.
     *
     * @param ex the ex
     *
     * @return the response entity
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    protected ResponseEntity<VeradatErrorMessage> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex)
    {
        VeradatErrorMessage em = veradatExceptionUtils.buildErrorMessage(ex, "El tipo de datos enviados no son soportados");
        return new ResponseEntity<>(em, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    /**
     * Handle http request method not supported response entity.
     *
     * @param ex the ex
     *
     * @return the response entity
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<VeradatErrorMessage> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex)
    {
        VeradatErrorMessage em = veradatExceptionUtils.buildErrorMessage(ex, "Tipo de Metodo http no es soportado para la operacion solicitada");
        return new ResponseEntity<>(em, HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * Handle http message not readable response entity.
     *
     * @param ex the ex
     *
     * @return the response entity
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<VeradatErrorMessage> handleHttpMessageNotReadable(HttpMessageNotReadableException ex)
    {
        VeradatErrorMessage em = veradatExceptionUtils.buildErrorMessage(ex, "El cuerpo del mensaje es requerido y no se envio o no se puede interpretar");
        return new ResponseEntity<>(em, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle method argument not valid response entity.
     *
     * @param ex the ex
     *
     * @return the response entity
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<VeradatErrorMessage> handleMethodArgumentNotValid(MethodArgumentNotValidException ex)
    {

        VeradatErrorMessage em = veradatExceptionUtils.buildErrorMessage(ex, "Los argumentos del mensaje no son validos");

        BindingResult result = ex.getBindingResult();
        List<org.springframework.validation.FieldError> fieldErrors = result.getFieldErrors();

        for (org.springframework.validation.FieldError fieldError : fieldErrors) {
            em.addFieldError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return new ResponseEntity<>(em, HttpStatus.BAD_REQUEST);
    }
}
