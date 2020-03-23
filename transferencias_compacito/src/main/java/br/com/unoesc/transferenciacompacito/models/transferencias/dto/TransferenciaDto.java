package br.com.unoesc.transferenciacompacito.models.transferencias.dto;

import br.com.unoesc.transferenciacompacito.models.transferencias.StatusRequerimento;
import br.com.unoesc.transferenciacompacito.models.transferencias.Transferencia;
import br.com.unoesc.transferenciacompacito.models.usuarios.Usuario;

public class TransferenciaDto {

    private Transferencia idTransferencia;
    private Usuario idUsuarioOrigem;
    private Usuario idUsuarioDestino;
    private StatusRequerimento idStatusRequerimento;

    // PRECISA FAZER UMA FUNCAO DE CONSULTA NO BANCO QUE RETORNE TODOS OS DADOS E CRIE OS OBJETOS
    // (FAZER UM SQL CRU E IR MONTANDO OS OBJETOS NUM LOOPING)
    public TransferenciaDto(Usuario usuarioOrigem, Usuario usuarioDestino, StatusRequerimento statusRequerimento) {
        this.idUsuarioOrigem = usuarioOrigem;
        this.idUsuarioDestino = usuarioDestino;
        this.idStatusRequerimento = statusRequerimento;
        this.idTransferencia = statusRequerimento.getIdTransferencia();
    }

    public TransferenciaDto(Usuario usuarioOrigem, Usuario usuarioDestino, Transferencia transferencia){
        this.idUsuarioOrigem = usuarioOrigem;
        this.idUsuarioDestino = usuarioDestino;
        this.idTransferencia = transferencia;
    }

    public Transferencia getIdTransferencia() {
        return idTransferencia;
    }

    public void setIdTransferencia(Transferencia idTransferencia) {
        this.idTransferencia = idTransferencia;
    }

    public Usuario getIdUsuarioOrigem() {
        return idUsuarioOrigem;
    }

    public void setIdUsuarioOrigem(Usuario idUsuarioOrigem) {
        this.idUsuarioOrigem = idUsuarioOrigem;
    }

    public Usuario getIdUsuarioDestino() {
        return idUsuarioDestino;
    }

    public void setIdUsuarioDestino(Usuario idUsuarioDestino) {
        this.idUsuarioDestino = idUsuarioDestino;
    }

    public StatusRequerimento getIdStatusRequerimento() {
        return idStatusRequerimento;
    }

    public void setIdStatusRequerimento(StatusRequerimento idStatusRequerimento) {
        this.idStatusRequerimento = idStatusRequerimento;
    }
}
