package br.com.unoesc.transferenciacompacito.repositorys.transferencias;

import br.com.unoesc.transferenciacompacito.models.transferencias.StatusRequerimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;

public interface StatusRequerimentoRepository extends JpaRepository<StatusRequerimento, Long> {

    @Query(nativeQuery = true,
        value ="SELECT * FROM status_requerimentos sr " +
                "WHERE sr.id_transferencia IN (:transferencias) AND sr.id_status = " +
                    "(select max(id_status) from status_requerimentos sr1 where sr.id_transferencia = sr1.id_transferencia)"
    )
    ArrayList<StatusRequerimento> findAllByIdTransferencia(List<Long> transferencias);
}
