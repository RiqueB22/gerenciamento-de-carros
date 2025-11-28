package com.exercicio.gerenciamento_de_carros.config.jwt;

import com.exercicio.gerenciamento_de_carros.service.jwt.JwtUserDetailsService;
import com.exercicio.gerenciamento_de_carros.utils.jwt.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.UUID;

//Classe JWTFiltro
@Component
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    //Atributos
    private final JwtUtils jwtUtils;
    private final JwtUserDetailsService userDetailsService;

    //O método autentica o usuário pela JWT
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        //Pega o header Authorization da requisição
        String authHeader = request.getHeader("Authorization");

        //Verifica se o header existe e começa com “Bearer “
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            //Extrai o token, removendo “Bearer “
            String token = authHeader.substring(7);

            try {
                //Extrai o ID do usuário de dentro do token
                UUID userId = jwtUtils.extrairId(token);

                //Carrega o usuário do banco
                JwtUserDetails userDetails =
                        (JwtUserDetails) userDetailsService.loadUserByUsername(userId.toString());

                //Cria um objeto de autenticação
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                //Adiciona informações da requisição (IP, sessão, etc.)
                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                //Coloca a autenticação no contexto do Spring Security
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {
                System.out.println("JWT inválido: " + e.getMessage());
            }
        }

        //Continua o filtro normalmente
        filterChain.doFilter(request, response);
    }
}