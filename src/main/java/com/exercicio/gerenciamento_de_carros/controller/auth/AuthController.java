package com.exercicio.gerenciamento_de_carros.controller.auth;

import com.exercicio.gerenciamento_de_carros.dto.request.LoginRequest;
import com.exercicio.gerenciamento_de_carros.dto.request.RegisterRequest;
import com.exercicio.gerenciamento_de_carros.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

//Classe controller de autenticação
@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
@Tag(name = "Autenticação", description = "Endpoints de login e registro")
public class AuthController {

    //Service autenticalção
    private final AuthService service;

    //Registro de usuario
    @PostMapping("/register")
    @Operation(summary = "Registra um novo usuário")
    public String register(@Valid @RequestBody RegisterRequest request) {
        return service.register(request);
    }

    //Loga o usuario
    @PostMapping("/login")
    @Operation(summary = "Faz login e retorna JWT")
    public String login(@Valid @RequestBody LoginRequest request) {
        return service.login(request);
    }
}

