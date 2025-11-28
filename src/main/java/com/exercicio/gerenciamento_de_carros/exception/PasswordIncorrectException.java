package com.exercicio.gerenciamento_de_carros.exception;

//Classe Exceção de senha incorreta
public class PasswordIncorrectException extends RuntimeException {
    public PasswordIncorrectException(String mensage) {
        super(mensage);
    }
}
