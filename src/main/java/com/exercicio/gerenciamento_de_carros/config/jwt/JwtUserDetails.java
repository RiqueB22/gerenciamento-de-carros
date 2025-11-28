package com.exercicio.gerenciamento_de_carros.config.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

//O usuário é autenticado pelo JWT, usando apenas o UUID e sem roles ou senha
@AllArgsConstructor
@Getter
public class JwtUserDetails implements UserDetails {

    //Atributo
    private final UUID id;

    //Retorna permissões do usuário
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); // sem roles
    }

    //Retorna a senha
    @Override
    public String getPassword() {
        return null; // não usamos senha aqui
    }

    //Retorna o id
    @Override
    public String getUsername() {
        return id.toString();
    }

    //Informa se a conta é valida
    @Override
    public boolean isAccountNonExpired() { return true; }

    //Informa se a conta não está bloqueada
    @Override
    public boolean isAccountNonLocked() { return true; }

    //Informa se a senha não expirou
    @Override
    public boolean isCredentialsNonExpired() { return true; }

    //Informa se a conta está habilitada
    @Override
    public boolean isEnabled() { return true; }
}

