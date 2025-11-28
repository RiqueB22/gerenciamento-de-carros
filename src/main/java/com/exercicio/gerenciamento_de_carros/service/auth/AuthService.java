package com.exercicio.gerenciamento_de_carros.service.auth;

import com.exercicio.gerenciamento_de_carros.utils.jwt.JwtUtils;
import com.exercicio.gerenciamento_de_carros.dto.request.LoginRequest;
import com.exercicio.gerenciamento_de_carros.dto.request.RegisterRequest;
import com.exercicio.gerenciamento_de_carros.entity.usuario.Usuario;
import com.exercicio.gerenciamento_de_carros.exception.ActiveNotActiveException;
import com.exercicio.gerenciamento_de_carros.exception.EmailUniqueViolationException;
import com.exercicio.gerenciamento_de_carros.exception.EntityNotFoundException;
import com.exercicio.gerenciamento_de_carros.exception.PasswordIncorrectException;
import com.exercicio.gerenciamento_de_carros.repository.usuario.UsuarioRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//Classe Service autenticação
@Service
@AllArgsConstructor
public class AuthService {

    //Repositorio usuario
    private final UsuarioRepositorio repository;
    //Criptografia de senha
    private final PasswordEncoder encoder;
    //JWTUtils
    private final JwtUtils jwtUtils;

    //Registro de usuario
    public String register(RegisterRequest request) {

        //Verifica se email já está cadastrado
        if (repository.findByEmail(request.email()).isPresent()) {
            throw new EmailUniqueViolationException("E-mail já está em uso");
        }

        //Coloca as informações no objeto
        var user = new Usuario();
        user.setNome(request.nome());
        user.setEmail(request.email());
        user.setSenha(encoder.encode(request.senha()));
        user.setAtivo(request.ativo());

        //Salva usuario
        repository.save(user);

        //Retorna o token gerado
        return jwtUtils.gerarToken(user.getId(), user.getEmail(), user.getNome());
    }

    //Login de usuario
    public String login(LoginRequest request) {

        //Checa se email foi encontrado
        var user = repository.findByEmail(request.email())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        //Checa se a senha está correta
        if (!encoder.matches(request.senha(), user.getSenha())) {
            throw new PasswordIncorrectException("Senha incorreta");
        }

        //Verifica se é ativo
        if (!user.getAtivo()) {
            throw new ActiveNotActiveException("Usuário inativo");
        }

        //Retorna o token
        return jwtUtils.gerarToken(user.getId(), user.getEmail(),  user.getNome());
    }
}


