package com.exercicio.gerenciamento_de_carros.repository.carro;

import com.exercicio.gerenciamento_de_carros.entity.carro.Carro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

//Interface do repositorio carro
@Repository
public interface CarroRepositorio extends JpaRepository<Carro, UUID> {
    //Faz buscas paginadas no banco de dados
    Page<Carro> findByMarcaContainingIgnoreCaseAndModeloContainingIgnoreCase(
            String modelo,
            String marca,
            Pageable pageable
    );
}
