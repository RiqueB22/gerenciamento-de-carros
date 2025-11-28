package com.exercicio.gerenciamento_de_carros.exception;

//Classe Exceção entidade não encontrada
public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String mensage){
        super(mensage);
    }
}
