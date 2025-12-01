package com.exercicio.gerenciamento_de_carros.entity.usuario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.time.OffsetDateTime;
import java.util.*;

//Classe entidade usuario
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails {

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
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeRole role;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime created_at;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private OffsetDateTime updated_at;

    //permissões/roles do usuário
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //Verifica se o usuário tem a role de administrador
        if (this.role == TypeRole.ADMIN) {
            //Se for admin, retorna uma lista com a autoridade "ROLE_ADMIN"
            return List.of(
                    new SimpleGrantedAuthority("ROLE_ADMIN")
            );
        }
        //Caso contrário, o usuário é considerado cliente e recebe a autoridade "ROLE_CLIENT"
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    //Compara a senha fornecida no login com a senha do usuário no banco
    @Override
    public String getPassword() {
        return senha;
    }

    //identificar qual é o usuario
    @Override
    public String getUsername() {
        return email;
    }

    //Verifica se a conta não expirou
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //Verifica se a conta não foi bloqueada
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //Verifica se as credenciais não expiraram
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //Verifica se está ativa a conta
    @Override
    public boolean isEnabled() {
        return Boolean.TRUE.equals(ativo);
    }
}
