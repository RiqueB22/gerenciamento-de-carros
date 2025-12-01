package com.exercicio.gerenciamento_de_carros.dto.request;

import com.exercicio.gerenciamento_de_carros.entity.usuario.TypeRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

//Classe DTO Request Register
public record RegisterRequest(
        @NotBlank
        @Size(max = 50)
        @Pattern(regexp = "^[A-Za-zÀ-ÿ ]+$", message = "Digite o nome corretamente com apenas letras!")
        String nome,
        @NotBlank
        @Size(max = 100)
        @Email( regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}+$", message = "O email deve ser válido")
        String email,
        @NotBlank
        @Size(min = 8)
        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z0-9]+$",
                message = "A senha deve ter ao menos uma letra e um número!"
        )
        String senha,
        @NotNull
        Boolean ativo,
        @NotNull
        @Schema(allowableValues = {"ADMIN", "CLIENT"})
        TypeRole role
) {}

