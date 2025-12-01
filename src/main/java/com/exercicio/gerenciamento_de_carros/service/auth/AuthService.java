package com.exercicio.gerenciamento_de_carros.service.auth;

import com.exercicio.gerenciamento_de_carros.dto.response.LoginResponse;
import com.exercicio.gerenciamento_de_carros.dto.response.ResponseUser;
import com.exercicio.gerenciamento_de_carros.service.security.TokenService;
import com.exercicio.gerenciamento_de_carros.dto.request.LoginRequest;
import com.exercicio.gerenciamento_de_carros.dto.request.RegisterRequest;
import com.exercicio.gerenciamento_de_carros.entity.usuario.Usuario;
import com.exercicio.gerenciamento_de_carros.exception.EmailUniqueViolationException;
import com.exercicio.gerenciamento_de_carros.repository.usuario.UsuarioRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//Classe Service autenticação
@Service
@AllArgsConstructor
public class AuthService {

    private final UsuarioRepositorio repository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final TokenService tokenService;

    //Registro de usuario
    public ResponseUser register(RegisterRequest request) {

        //Verifica se email já está cadastrado
        if (this.repository.findByEmail(request.email()) != null) {
            throw new EmailUniqueViolationException("E-mail já está em uso");
        }

        //Coloca as informações no objeto
        var user = new Usuario();
        user.setNome(request.nome());
        user.setEmail(request.email());
        user.setSenha(encoder.encode(request.senha()));
        user.setRole(request.role());
        user.setAtivo(request.ativo());

        //Salva usuario
        repository.save(user);

        //Retorna o token gerado
        return new ResponseUser(
                user.getId(),
                user.getNome(),
                user.getEmail(),
                user.getRole(),
                user.getAtivo()
        );
    }

    //Login de usuario
    public LoginResponse login(LoginRequest request) {
        //Objeto para ser autenticado
        var usernamePassword = new UsernamePasswordAuthenticationToken(request.email(), request.senha());

        //Autentica o usuario
        var auth = authManager.authenticate(usernamePassword);

        //gera token
        var token = tokenService.generateToken((Usuario) auth.getPrincipal());

        //Retorna o token
        return new LoginResponse(
                token
        );
    }
}
