package com.exercicio.gerenciamento_de_carros.exception;

//Classe Exceção não ativo
public class ActiveNotActiveException extends RuntimeException{
    public ActiveNotActiveException(String mensage){
        super(mensage);
    }
}
