package com.exercicio.gerenciamento_de_carros.repository.usuario;

import com.exercicio.gerenciamento_de_carros.entity.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import java.util.UUID;

//Interface do repositorio do usuario
@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, UUID> {
    //Procura um usuário pelo email
    Usuario findByEmail(String email);
}
