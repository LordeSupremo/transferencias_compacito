package br.com.unoesc.transferenciacompacito.models.transferencias.dto;

import br.com.unoesc.transferenciacompacito.models.transferencias.StatusRequerimento;
import br.com.unoesc.transferenciacompacito.models.usuarios.Usuario;

import java.util.Date;

public class ExtratoTransferenciaDto {

    private Long idTransferencia;
    private String nomeRemetente;
    private String emailRemetente;
    private String nomeDestinatario;
    private String emailDestinatario;
    private String status;
    private Date data;
    private Integer valor;

    public ExtratoTransferenciaDto(Usuario remetente, Usuario destinatario, StatusRequerimento status) {
        this.idTransferencia = status.getIdTransferencia().getId();
        this.nomeRemetente = remetente.getNome();
        this.emailRemetente = remetente.getEmail();
        this.nomeDestinatario = destinatario.getNome();
        this.emailDestinatario = destinatario.getEmail();
        this.status = status.getIdStatus();
        this.data = status.getDataStatus();
        this.valor = status.getIdTransferencia().getValor();
    }

    public Long getIdTransferencia() {
        return idTransferencia;
    }

    public void setIdTransferencia(Long idTransferencia) {
        this.idTransferencia = idTransferencia;
    }

    public String getNomeRemetente() {
        return nomeRemetente;
    }

    public void setNomeRemetente(String nomeRemetente) {
        this.nomeRemetente = nomeRemetente;
    }

    public String getEmailRemetente() {
        return emailRemetente;
    }

    public void setEmailRemetente(String emailRemetente) {
        this.emailRemetente = emailRemetente;
    }

    public String getNomeDestinatario() {
        return nomeDestinatario;
    }

    public void setNomeDestinatario(String nomeDestinatario) {
        this.nomeDestinatario = nomeDestinatario;
    }

    public String getEmailDestinatario() {
        return emailDestinatario;
    }

    public void setEmailDestinatario(String emailDestinatario) {
        this.emailDestinatario = emailDestinatario;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }
}
