package com.exercicio.gerenciamento_de_carros.dto.request;

import jakarta.validation.constraints.*;

//Classe DTO Request Register
public record RegisterRequest(
        @NotBlank
        @Size(max = 50)
        @Pattern(regexp = "^[A-zÀ-ÿ ]+$", message = "Digite o nome corretamente com apenas letras!")
        String nome,
        @NotBlank
        @Size(max = 100)
        @Email( regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}+$", message = "O email deve ser válido")
        String email,
        @NotBlank
        @Size(min = 8)
        @Pattern(regexp = "^[A-z0-9]+$", message = "Digite a senha corretamente com letra e numero!")
        String senha,
        @NotNull
        Boolean ativo
) {}

