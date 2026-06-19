package com.example.sistemagerenciamentotabulando.exceptions;

public class NumeroInvalidoException extends Exception {
    public NumeroInvalidoException(String campo) {
        super("Valor inválido para o campo '" + campo + "'.");
    }
}
