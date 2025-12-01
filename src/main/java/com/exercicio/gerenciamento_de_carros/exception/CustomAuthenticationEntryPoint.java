package com.exercicio.gerenciamento_de_carros.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

//Autenticação entre pontos
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    //verifica se está sendo autenticado corretamente
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json; charset=UTF-8");

        String json = "{\"error\": \"Não autenticado. Você precisa fazer login.\"}";
        response.getWriter().write(json);
    }
}

