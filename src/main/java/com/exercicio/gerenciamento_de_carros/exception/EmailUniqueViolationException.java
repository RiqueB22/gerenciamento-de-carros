package com.exercicio.gerenciamento_de_carros.exception;

//Classe Exceção de email unico
public class EmailUniqueViolationException extends RuntimeException {
    public EmailUniqueViolationException(String format) {
        super(format);
    }
}
