package com.exercicio.gerenciamento_de_carros.service.carro;

import com.exercicio.gerenciamento_de_carros.dto.request.RequestCar;
import com.exercicio.gerenciamento_de_carros.dto.response.ResponseCar;
import com.exercicio.gerenciamento_de_carros.entity.carro.Carro;
import com.exercicio.gerenciamento_de_carros.exception.EntityNotFoundException;
import com.exercicio.gerenciamento_de_carros.repository.carro.CarroRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

//Classe Service de carro
@RequiredArgsConstructor
@Service
public class CarroService {

    //Repositorio carro
    private final CarroRepositorio carroRepositorio;

    //Criar carro
    @Transactional
    public ResponseCar salvar(RequestCar carro) {
        var car = new Carro();
        //Copia os atributos do DTO Request para um outro objeto
        BeanUtils.copyProperties(carro, car);
        //Salva no banco de dados
        carroRepositorio.save(car);

        //Retorna em um DTO Response
        return new ResponseCar(
                car.getId(),
                car.getModelo(),
                car.getMarca(),
                car.getCor(),
                car.getAno(),
                car.getData_de_criacao(),
                car.getAtivo()
        );
    }

    //Lista os carros com filtro
    public Page<ResponseCar> listarFiltradoPaginado(RequestCar filtro, int page, int size) {
        //Cria a páginação
        Pageable pageable = PageRequest.of(page, size);

        //Define filtros
        String marca = filtro != null && filtro.marca() != null ? filtro.marca() : "";
        String modelo = filtro != null && filtro.modelo() != null ? filtro.modelo() : "";

        //Consulta paginada no repositório
        Page<Carro> carrosPage = carroRepositorio
                .findByMarcaContainingIgnoreCaseAndModeloContainingIgnoreCase(
                        modelo,
                        marca,
                        pageable
                );

        //Retorna carros paginados em DTO Response
        return carrosPage.map(car -> new ResponseCar(
                car.getId(),
                car.getModelo(),
                car.getMarca(),
                car.getCor(),
                car.getAno(),
                car.getData_de_criacao(),
                car.getAtivo()
        ));
    }

    //Busca carro por id
    @Transactional(readOnly = true)
    public ResponseCar buscarPorId(UUID id) {
        //Busca carro pelo id
        var car = carroRepositorio.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Carro id=%s não encontrado!", id))
        );

        //Retorna em um DTO Response
        return new  ResponseCar(
                car.getId(),
                car.getModelo(),
                car.getMarca(),
                car.getCor(),
                car.getAno(),
                car.getData_de_criacao(),
                car.getAtivo()
        );
    }

    //Atualiza todas as informações do carro
    @Transactional
    public ResponseCar editar(UUID id, RequestCar carro) {
        //Busca pelo id
        var car = carroRepositorio.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Carro não encontrado!")
        );

        //Copia os atributos do DTO Request para um outro objeto
        BeanUtils.copyProperties(carro, car);
        //Salva no banco de dados
        carroRepositorio.save(car);

        //Retorna em um DTO Response
        return new ResponseCar(
                car.getId(),
                car.getModelo(),
                car.getMarca(),
                car.getCor(),
                car.getAno(),
                car.getData_de_criacao(),
                car.getAtivo()
        );
    }

    //Atualiza parcialmente as informações do carro
    @Transactional
    public ResponseCar editarParcial(UUID id, Map<String, Object> updates) {
        //Busca pelo id
        var car = carroRepositorio.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Carro não encontrado!")
        );
        //Atualiza cada campo que foi informado
        updates.forEach((key, value) -> {
            switch (key) {
                case "modelo" -> car.setModelo((String) value);
                case "marca" -> car.setMarca((String) value);
                case "ano" -> car.setAno((Integer) value);
                case "cor" -> car.setCor((String) value);
                case "data_de_criacao" -> car.setData_de_criacao((LocalDate) value);
                case "ativo" -> car.setAtivo((Boolean) value);
            }
        });

        //Salva as alterações
        carroRepositorio.save(car);

        //Retorna um DTO Response
        return new ResponseCar(
                car.getId(),
                car.getModelo(),
                car.getMarca(),
                car.getCor(),
                car.getAno(),
                car.getData_de_criacao(),
                car.getAtivo()
        );
    }

    //Deleta carro
    public ResponseCar deletar(UUID id) {

        //Busca pelo id
        var car = carroRepositorio.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Carro não encontrado!")
        );

        //Deleta o carro
        carroRepositorio.delete(car);

        //Retorna um DTO Response
        return new ResponseCar(
                car.getId(),
                car.getModelo(),
                car.getMarca(),
                car.getCor(),
                car.getAno(),
                car.getData_de_criacao(),
                car.getAtivo()
        );
    }
}
