package br.com.portoseguro.alteracaodados.domain.exceptions;

import lombok.Getter;

@Getter
public class ValidationError extends RuntimeException {
    int errorCode;
    public ValidationError(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

}
