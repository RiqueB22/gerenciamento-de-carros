package com.exercicio.gerenciamento_de_carros.service.security;

import com.exercicio.gerenciamento_de_carros.repository.usuario.UsuarioRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

//UserDetailsService
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    //Repositorio usuario
    private final UsuarioRepositorio repository;

    //Carrega username
    @Override
    public UserDetails loadUserByUsername(String username) {
        return repository.findByEmail(username);
    }
}

