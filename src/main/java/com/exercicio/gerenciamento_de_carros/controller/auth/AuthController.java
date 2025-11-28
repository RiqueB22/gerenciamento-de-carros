package com.exercicio.gerenciamento_de_carros.controller.auth;

import com.exercicio.gerenciamento_de_carros.dto.request.LoginRequest;
import com.exercicio.gerenciamento_de_carros.dto.request.RegisterRequest;
import com.exercicio.gerenciamento_de_carros.dto.response.ResponseCar;
import com.exercicio.gerenciamento_de_carros.exception.global.ErrorMessage;
import com.exercicio.gerenciamento_de_carros.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @Operation(summary = "Registra um novo usuario", responses = {
            @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseCar.class))),
            @ApiResponse(responseCode = "409", description = "email já cadastrado no sistema",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "422", description = "Recursos não processado por dados de entrada invalidos",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.register(request));
    }

    //Loga o usuario
    @Operation(summary = "Loga o usuario e retorna um token", responses = {
            @ApiResponse(responseCode = "200", description = "Recurso criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseCar.class))),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "400", description = "Senha incorreta",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "409", description = "Usuario inativo",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "422", description = "Recursos não processado por dados de entrada invalidos",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(service.login(request));
    }
}

