package com.exercicio.gerenciamento_de_carros.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

//Classe DTO Request Login
public record LoginRequest(
        @NotBlank
        @Email(message = "O email deve ser válido")
        String email,
        @NotBlank
        @Size(min = 8)
        @Pattern(regexp = "^[A-z0-9]+$", message = "Digite a senha corretamente com letras e numeros!")
        String senha) {
}
