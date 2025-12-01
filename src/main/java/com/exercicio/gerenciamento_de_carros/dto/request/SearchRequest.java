package com.exercicio.gerenciamento_de_carros.dto.request;

import com.exercicio.gerenciamento_de_carros.utils.ValidationGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SearchRequest(
        @NotBlank(groups = {ValidationGroup.Create.class})
        @Pattern(regexp = "^[A-zÀ-ÿ0-9 ]+$", message = "Digite o modelo corretamente com letras e numeros!", groups = {ValidationGroup.Create.class, ValidationGroup.Patch.class})
        @Size(min = 1, max = 50)
        String modelo,
        @NotBlank(groups = {ValidationGroup.Create.class})
        @Pattern(regexp = "^[A-z]+$", message = "Digite a marca corretamente com letras sem acento!", groups = {ValidationGroup.Create.class, ValidationGroup.Patch.class})
        @Size(min = 1, max = 50)
        String marca
) {
}
