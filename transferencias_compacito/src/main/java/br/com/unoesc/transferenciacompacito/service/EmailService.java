package br.com.unoesc.transferenciacompacito.service;

import br.com.unoesc.transferenciacompacito.models.transferencias.dto.TransferenciaDto;
import br.com.unoesc.transferenciacompacito.models.usuarios.Usuario;
import org.apache.commons.mail.*;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void enviar(String emailDestinatario, String titulo, String mensagem) throws EmailException {
        Email email = new SimpleEmail();
        email.setHostName("smtp.gmail.com");
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator("joaoterceiro366@gmail.com", "magica123"));
        email.setSSLOnConnect(true);

        email.setFrom("joaoterceiro366@gmail.com");
        email.setSubject(titulo);
        email.setMsg(mensagem);
        email.addTo(emailDestinatario);
        email.send();
    }

    public void emailTransferencia(TransferenciaDto transferenciaDto) throws EmailException {
        int valor = transferenciaDto.getIdTransferencia().getValor();
        Usuario remetente = transferenciaDto.getIdUsuarioOrigem();
        Usuario destinatario = transferenciaDto.getIdUsuarioDestino();

        enviar(remetente.getEmail(), "Transferencia Realizada",
            "Transferencia no valor de " + valor + " CCoin realizada para " + destinatario.getNome() + "."
        );

        enviar(destinatario.getEmail(), "Transferencia Recebida",
            "Transferencia no valor de " + valor + " CCoin recebida de " + remetente.getNome() + "."
        );
    }
}
