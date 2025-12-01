package com.exercicio.gerenciamento_de_carros.controller.carro;

import com.exercicio.gerenciamento_de_carros.dto.request.RequestCar;
import com.exercicio.gerenciamento_de_carros.dto.request.SearchRequest;
import com.exercicio.gerenciamento_de_carros.dto.response.ResponseCar;
import com.exercicio.gerenciamento_de_carros.exception.global.ErrorMessage;
import com.exercicio.gerenciamento_de_carros.service.carro.CarroService;
import com.exercicio.gerenciamento_de_carros.utils.ValidationGroup;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.UUID;

//Classe controller carro
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/carros")
@Tag(name = "Carros", description = "Endpoints de get, getById, search, put, patch, post e delete")
public class CarroController {

    //Service carro
    private final CarroService carroService;

    //Cria carro
    @Operation(summary = "Registra um novo carro", description = "Requisição exige um Bearer Token, Acesso restrito a USER",
            security = @SecurityRequirement(name = "security"),
            responses = {
            @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseCar.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso negado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "422", description = "Recursos não processado por dados de entrada invalidos",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PostMapping
    public ResponseEntity<ResponseCar> create(@Validated(ValidationGroup.Create.class) @RequestBody RequestCar carro) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carroService.salvar(carro));
    }

    //Paginação e filtrada de carros
    @Operation(summary = "Seleciona uma lista de carros paginada e filtrada", description = "Requisição exige um Bearer Token, Acesso restrito a USER",
            security = @SecurityRequirement(name = "security"),
            responses = {
            @ApiResponse(responseCode = "200", description = "Recursos recuperados com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseCar.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso negado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "422", description = "Recursos não processado por dados de entrada invalidos",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PostMapping("/search")
    public ResponseEntity<Page<ResponseCar>> listarFiltrado(
            @Validated(ValidationGroup.Patch.class)
            @RequestBody(required = false) SearchRequest filtro,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(carroService.listarFiltradoPaginado(filtro, page, size));
    }

    //Busca pelo id
    @Operation(summary = "Seleciona pelo id de carro", description = "Requisição exige um Bearer Token, Acesso restrito a USER",
            security = @SecurityRequirement(name = "security"),
            responses = {
            @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseCar.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso negado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ResponseCar> getById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(carroService.buscarPorId(id));
    }

    //Atualiza todas as informações do carro
    @Operation(summary = "Atualiza todas as informações de carro", description = "Requisição exige um Bearer Token, Acesso restrito a USER",
            security = @SecurityRequirement(name = "security"),
            responses = {
            @ApiResponse(responseCode = "200", description = "Recurso atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseCar.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso negado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "422", description = "Recursos não processado por dados de entrada invalidos",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<ResponseCar> update(@PathVariable UUID id, @Validated(ValidationGroup.Create.class) @RequestBody RequestCar carro) {
        return ResponseEntity.status(HttpStatus.OK).body(carroService.editar(id, carro));
    }

    //Atualiza parcialmente as informações do carro
    @Operation(summary = "Atualiza parcialmente as informações de carro", description = "Requisição exige um Bearer Token, Acesso restrito a USER",
            security = @SecurityRequirement(name = "security"),
            responses = {
            @ApiResponse(responseCode = "200", description = "Recurso atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseCar.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso negado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "422", description = "Recursos não processado por dados de entrada invalidos",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PatchMapping("/{id}")
    public ResponseEntity<ResponseCar> updateParcial(@PathVariable UUID id, @Validated(ValidationGroup.Patch.class) @RequestBody Map<String, Object> updates) {
        return ResponseEntity.status(HttpStatus.OK).body(carroService.editarParcial(id, updates));
    }

    //Deleta o carro
    @Operation(summary = "Deleta o carro", description = "Requisição exige um Bearer Token, Acesso restrito a USER",
            security = @SecurityRequirement(name = "security"),
            responses = {
            @ApiResponse(responseCode = "200", description = "Recurso deletado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseCar.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso negado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseCar> delete(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(carroService.deletar(id));
    }
}
