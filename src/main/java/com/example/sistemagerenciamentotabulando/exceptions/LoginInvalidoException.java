package com.example.sistemagerenciamentotabulando.exceptions;

public class LoginInvalidoException extends Exception {
    public LoginInvalidoException() {
        super("Usuário ou senha incorretos.");
    }
}
