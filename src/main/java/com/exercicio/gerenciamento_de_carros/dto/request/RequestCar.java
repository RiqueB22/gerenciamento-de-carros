package com.exercicio.gerenciamento_de_carros.dto.request;

import com.exercicio.gerenciamento_de_carros.utils.ValidationGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

//Classe DTO Request Car
public record RequestCar(
        @NotBlank(groups = {ValidationGroup.Create.class})
        @Pattern(regexp = "^[A-zÀ-ÿ0-9 ]+$", message = "Digite o modelo corretamente com letras e numeros!", groups = {ValidationGroup.Create.class, ValidationGroup.Patch.class})
        @Size(min = 1, max = 50)
        String modelo,
        @NotBlank(groups = {ValidationGroup.Create.class})
        @Pattern(regexp = "^[A-z]+$", message = "Digite a marca corretamente com letras sem acento!", groups = {ValidationGroup.Create.class, ValidationGroup.Patch.class})
        @Size(min = 1, max = 50)
        String marca,
        @NotBlank(groups = {ValidationGroup.Create.class})
        @Pattern(regexp = "^[A-z]+$", message = "Digite a cor corretamente com letras sem acento!", groups = {ValidationGroup.Create.class, ValidationGroup.Patch.class})
        @Size(min = 1, max = 50)
        String cor,
        @NotNull(groups = {ValidationGroup.Create.class})
        Integer ano,
        @NotNull(groups = {ValidationGroup.Create.class})
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate data_de_criacao,
        @NotNull(groups = {ValidationGroup.Create.class})
        Boolean ativo) {
}
