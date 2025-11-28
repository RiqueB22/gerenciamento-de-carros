package com.exercicio.gerenciamento_de_carros.entity.carro;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

//Classe entidade carro
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "carros")
public class Carro {

    //Atributos
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;
    @Column(name = "modelo", length = 50)
    private String modelo;
    @Column(name = "marca", length = 50)
    private String marca;
    @Column(name = "cor", length = 50)
    private String cor;
    @Column(name = "ano")
    private Integer ano;
    @Column(name = "data_de_criacao")
    private LocalDate data_de_criacao;
    @Column(name = "ativo")
    private Boolean ativo;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime created_at;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updated_at;
}
