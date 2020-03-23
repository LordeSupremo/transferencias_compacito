package br.com.unoesc.transferenciacompacito.models.transferencias;

import br.com.unoesc.transferenciacompacito.form.TransferenciaFORM;

import javax.persistence.*;

@Entity
@Table(name = "transferencias")
public class Transferencia {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="id_usuario_origem")
    private Long idUsuarioOrigem;
    @Column(name="id_usuario_destino")
    private Long idUsuarioDestino;
    private Integer valor;

    public Transferencia(){ }

    public Transferencia(TransferenciaFORM form){
        this.idUsuarioOrigem = form.getIdRemetente();
        this.idUsuarioDestino = form.getIdDestinatario();
        this.valor = form.getValor();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUsuarioOrigem() {
        return idUsuarioOrigem;
    }

    public void setIdUsuarioOrigem(Long id_remetente) {
        this.idUsuarioOrigem = id_remetente;
    }

    public Long getIdUsuarioDestino() {
        return idUsuarioDestino;
    }

    public void setIdUsuarioDestino(Long id_destinatario) {
        this.idUsuarioDestino = id_destinatario;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }
}
