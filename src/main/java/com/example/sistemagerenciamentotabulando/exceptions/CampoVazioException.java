package com.example.sistemagerenciamentotabulando.exceptions;

public class CampoVazioException extends Exception {

    public CampoVazioException(String campo) {
        super("O campo '" + campo + "' não pode ficar vazio.");
    }
}
