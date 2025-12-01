package com.exercicio.gerenciamento_de_carros.config.jwt;

import com.exercicio.gerenciamento_de_carros.repository.usuario.UsuarioRepositorio;
import com.exercicio.gerenciamento_de_carros.service.security.TokenService;
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

//Filtro de segurança
@Component
@AllArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    //Token Service
    private TokenService tokenService;

    //Repositorio de usuario
    private UsuarioRepositorio usuarioRepositorio;

    //Faz a filtragem
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        //Recupera o token
        var token = this.recoverToken(request);
        //Verifica se o token existe
        if (token != null) {
            //Valida o token
            var email = tokenService.validateToken(token);
            //Busca o usuário no banco de dados pelo email extraído do token
            var user = usuarioRepositorio.findByEmail(email);

            //Cria um objeto Authentication do Spring Security com o usuário autenticado
            var authentication = new UsernamePasswordAuthenticationToken(user,
                    null,
                    user.getAuthorities());
            //Associa detalhes da requisição ao objeto de autenticação
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            //Coloca o usuário autenticado no contexto de segurança do Spring
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        //Continua a execução da cadeia de filtros da aplicação
        filterChain.doFilter(request, response);
    }

    //Recupera o token
    private String recoverToken(HttpServletRequest request) {
        //Pega o valor do header
        var authHeader = request.getHeader("Authorization");
        //Verifica se o cabeçalho não é nulo e se começa com "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer "))
            return authHeader.replace("Bearer ", "");

        //Se não houver cabeçalho ou ele não estiver no formato esperado
        return null;
    }
}
