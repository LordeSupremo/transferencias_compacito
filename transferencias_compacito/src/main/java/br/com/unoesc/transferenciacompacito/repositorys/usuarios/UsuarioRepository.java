package br.com.unoesc.transferenciacompacito.repositorys.usuarios;

import br.com.unoesc.transferenciacompacito.models.usuarios.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM usuarios WHERE id IN (:usuarios)")
    ArrayList<Usuario> findAllById(List<Long> usuarios);
}
