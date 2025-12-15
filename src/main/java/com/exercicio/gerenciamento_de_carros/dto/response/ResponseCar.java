package com.exercicio.gerenciamento_de_carros.dto.response;

import java.time.LocalDate;
import java.util.UUID;

//Classe DTO Response Car
public record ResponseCar(UUID id, String modelo, String marca, String cor, Integer ano,
                          LocalDate created_at, Boolean ativo) {
}
