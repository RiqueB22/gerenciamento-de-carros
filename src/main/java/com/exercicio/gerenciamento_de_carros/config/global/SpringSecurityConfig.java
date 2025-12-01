package com.exercicio.gerenciamento_de_carros.config.global;

import com.exercicio.gerenciamento_de_carros.config.jwt.SecurityFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//Classe de configuração do Spring Security
@Configuration
@AllArgsConstructor
public class SpringSecurityConfig {
    //Filtro
    SecurityFilter securityFilter;

    //Configura o Spring Security
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)//Desativa CSRF
                //Define a política de sessão
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                //Configura as rotas
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/v1/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/register").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/v1/carros").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/v1/carros/search").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/v1/carros/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/carros/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH,"/api/v1/carros/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/carros/{id}").hasRole("ADMIN")
                        .requestMatchers("/v3/api-docs/swagger-ui/index.html").permitAll()
                        .requestMatchers("/v3/api-docs/docs-cars.html").permitAll()
                        .anyRequest().permitAll()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                //Constrói e retorna a cadeia de filtros
                .build();
    }

    //criptografa a senha
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //Faz a autenticação do Login no Spring Security
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config
    ) throws Exception {
        return config.getAuthenticationManager();
    }
}

