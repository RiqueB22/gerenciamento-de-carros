package com.exercicio.gerenciamento_de_carros.dto.response;

import com.exercicio.gerenciamento_de_carros.entity.usuario.TypeRole;

import java.util.UUID;

//Response DTO Usuario
public record ResponseUser(UUID id, String nome, String email,
                           TypeRole role, Boolean ativo) {
}
