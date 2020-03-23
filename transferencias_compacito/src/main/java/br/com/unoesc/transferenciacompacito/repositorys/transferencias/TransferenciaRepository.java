package br.com.unoesc.transferenciacompacito.repositorys.transferencias;

import br.com.unoesc.transferenciacompacito.models.transferencias.Transferencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface TransferenciaRepository extends JpaRepository<Transferencia, Long> {

    ArrayList<Transferencia> findAllByIdUsuarioOrigem(Long id);
}
