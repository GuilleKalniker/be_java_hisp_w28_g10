package com.mercadolibre.be_java_hisp_w28_g10.exception;

public class SaveOperationException extends RuntimeException{
    public SaveOperationException(String message) {
        super(message);
    }
}
