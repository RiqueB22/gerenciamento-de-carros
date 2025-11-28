package com.exercicio.gerenciamento_de_carros.controller.carro;

import com.exercicio.gerenciamento_de_carros.dto.request.RequestCar;
import com.exercicio.gerenciamento_de_carros.dto.response.ResponseCar;
import com.exercicio.gerenciamento_de_carros.service.carro.CarroService;
import com.exercicio.gerenciamento_de_carros.utils.ValidationGroup;
import io.swagger.v3.oas.annotations.Operation;
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
@Tag(name = "Carros", description = "Endpoints de get, getById, put, patch, post e delete")
public class CarroController {

    //Service carro
    private final CarroService carroService;

    //Cria carro
    @PostMapping
    @Operation(summary = "Registra um novo carro")
    public ResponseEntity<ResponseCar> create(@Validated(ValidationGroup.Create.class) @RequestBody RequestCar carro) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carroService.salvar(carro));
    }

    //Paginação e filtrada de carros
    @PostMapping("/search")
    @Operation(summary = "Seleciona todos os carros e permite filtrar")
    public ResponseEntity<Page<ResponseCar>> listarFiltrado(
            @Validated(ValidationGroup.Patch.class)
            @RequestBody(required = false) RequestCar filtro,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(carroService.listarFiltradoPaginado(filtro, page, size));
    }

    //Busca pelo id
    @GetMapping("/{id}")
    @Operation(summary = "Seleciona um carro")
    public ResponseEntity<ResponseCar> getById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(carroService.buscarPorId(id));
    }

    //Atualiza todas as informações do carro
    @PutMapping("/{id}")
    @Operation(summary = "Atualiza todas as informações do carro")
    public ResponseEntity<ResponseCar> update(@PathVariable UUID id, @Validated(ValidationGroup.Create.class) @RequestBody RequestCar carro) {
        return ResponseEntity.status(HttpStatus.OK).body(carroService.editar(id, carro));
    }

    //Atualiza parcialmente as informações do carro
    @PatchMapping("/{id}")
    @Operation(summary = "Atualiza uma informação de carro")
    public ResponseEntity<ResponseCar> updateParcial(@PathVariable UUID id, @Validated(ValidationGroup.Patch.class) @RequestBody Map<String, Object> updates) {
        return ResponseEntity.status(HttpStatus.OK).body(carroService.editarParcial(id, updates));
    }

    //Deleta o carro
    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um carro")
    public ResponseEntity<ResponseCar> delete(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(carroService.deletar(id));
    }
}
