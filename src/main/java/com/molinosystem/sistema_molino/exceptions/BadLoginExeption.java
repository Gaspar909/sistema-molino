package com.molinosystem.sistema_molino.exceptions;

public class BadLoginExeption extends RuntimeException {
    public BadLoginExeption(String message){
        super(message);
    }
}
