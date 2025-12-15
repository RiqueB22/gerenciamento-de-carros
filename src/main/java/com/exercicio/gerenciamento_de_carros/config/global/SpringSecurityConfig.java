package com.exercicio.gerenciamento_de_carros.config.global;

import com.exercicio.gerenciamento_de_carros.config.jwt.SecurityFilter;
import com.exercicio.gerenciamento_de_carros.exception.CustomAccessDeniedHandler;
import com.exercicio.gerenciamento_de_carros.exception.CustomAuthenticationEntryPoint;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

//Classe de configuração do Spring Security
@Configuration
public class SpringSecurityConfig {

    private static final String[] DOCUMENTATION_OPENAPI = {
            "/docs/index.html",
            "/docs-cars.html",
            "/docs-cars/**",
            "/v3/api-docs/**",
            "/swagger-ui-custom.html",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/**.html",
            "/webjars/**",
            "/configuration/**",
            "/swagger-resources/**"
    };

    //Filtro
    SecurityFilter securityFilter;
    //Autenticação exceção
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    //Exceção acesso restrito
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    public SpringSecurityConfig(SecurityFilter securityFilter, CustomAccessDeniedHandler customAccessDeniedHandler) {
        this.securityFilter = securityFilter;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
    }

    //Configura o Spring Security
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomAuthenticationEntryPoint customAuthenticationEntryPoint) throws Exception {

        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)//Desativa CSRF
                //Define a política de sessão
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                //Configura as rotas
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/v1/login", "/api/v1/register").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/api/v1/carros/**").hasRole("ADMIN")
                        .requestMatchers(DOCUMENTATION_OPENAPI).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler(customAccessDeniedHandler)
                        .authenticationEntryPoint(customAuthenticationEntryPoint))
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

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Allow specific origins (configure based on environment)
        configuration.setAllowedOriginPatterns(
                List.of("http://localhost:3000"));

        // Allow common HTTP methods
        configuration.setAllowedMethods(
                Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "PATCH"));

        // Allow common headers
        configuration.setAllowedHeaders(
                Arrays.asList(
                        "Origin",
                        "Content-Type",
                        "Accept",
                        "Authorization"));

        // Allow credentials (cookies, authorization headers)
        configuration.setAllowCredentials(true);

        // Cache preflight response for 1 hour
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
