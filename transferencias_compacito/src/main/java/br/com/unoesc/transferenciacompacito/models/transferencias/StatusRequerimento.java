package br.com.unoesc.transferenciacompacito.models.transferencias;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "status_requerimentos")
public class StatusRequerimento {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="id_transferencia", nullable=false)
    private Transferencia idTransferencia;

    @Column(name="id_status")
    @Enumerated(EnumType.ORDINAL)
    private StatusRequerimentosEnum idStatus;

    @Column(name="data_status")
    private Date dataStatus;

    public StatusRequerimento(){}

    public StatusRequerimento(Transferencia transferencia, StatusRequerimentosEnum idStatus){
        this.idTransferencia = transferencia;
        this.idStatus = idStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Transferencia getIdTransferencia() {
        return idTransferencia;
    }

    public void setIdTransferencia(Transferencia idTransferencia) {
        this.idTransferencia = idTransferencia;
    }

    public Date getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(Date dateStatus) {
        this.dataStatus = dateStatus;
    }

    public String getIdStatus() {
        return idStatus.name();
    }

    public void setIdStatus(StatusRequerimentosEnum idStatus) {
        this.idStatus = idStatus;
    }
}
