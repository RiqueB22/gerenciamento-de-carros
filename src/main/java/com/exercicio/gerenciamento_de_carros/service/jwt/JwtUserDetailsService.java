package com.exercicio.gerenciamento_de_carros.service.jwt;

import com.exercicio.gerenciamento_de_carros.config.jwt.JwtUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.UUID;

//Carrega o usuário para autenticação pelo JWT
@Service
public class JwtUserDetailsService implements UserDetailsService {

    //Carrega os dados de um usuário para autenticação
    @Override
    public UserDetails loadUserByUsername(String id) {
        //Convertendo para UUID
        return new JwtUserDetails(UUID.fromString(id));
    }
}

