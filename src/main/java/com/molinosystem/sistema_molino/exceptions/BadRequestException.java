package com.molinosystem.sistema_molino.exceptions;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String messasge){
        super(messasge);
    }
}
