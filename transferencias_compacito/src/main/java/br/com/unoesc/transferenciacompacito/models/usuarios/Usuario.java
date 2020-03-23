package br.com.unoesc.transferenciacompacito.models.usuarios;

import br.com.unoesc.transferenciacompacito.exception.SaldoInsuficienteException;

import javax.persistence.*;

@Entity
@Table(name = "usuarios", schema = "usuarios_local")
public class Usuario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private Integer credito;

    public Integer getCredito() {
        return credito;
    }


    public Usuario(){}

    public Usuario(Long id, String nome, String email){
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    public Usuario(Long id, String nome, String email, Integer credito){
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.credito = credito;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public void depositar(Integer credito) {
        this.credito += credito;
    }

    public Boolean sacar(Integer valor) {
        if(this.credito >= valor){
            this.credito -= valor;
            return Boolean.TRUE;
        }
        throw new SaldoInsuficienteException("Saldo insuficiente! Saldo: " + this.credito + " Valor: " + valor);
    }

}
