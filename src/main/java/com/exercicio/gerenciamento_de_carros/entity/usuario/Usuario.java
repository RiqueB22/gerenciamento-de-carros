package com.exercicio.gerenciamento_de_carros.entity.usuario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.OffsetDateTime;
import java.util.UUID;

//Classe entidade usuario
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {

    //Atributos
    @Id
    @GeneratedValue
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;
    @Column(name = "nome", length = 50, nullable = false)
    private String nome;
    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;
    @Column(name = "senha", nullable = false)
    private String senha;
    @Column(name = "ativo")
    private Boolean ativo;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime created_at;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private OffsetDateTime updated_at;
}
