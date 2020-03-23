package br.com.unoesc.transferenciacompacito.controllers;

import br.com.unoesc.transferenciacompacito.form.TransferenciaFORM;
import br.com.unoesc.transferenciacompacito.models.transferencias.*;
import br.com.unoesc.transferenciacompacito.models.transferencias.dto.ExtratoTransferenciaDto;
import br.com.unoesc.transferenciacompacito.models.transferencias.dto.TransferenciaDto;
import br.com.unoesc.transferenciacompacito.models.usuarios.Usuario;
import br.com.unoesc.transferenciacompacito.repositorys.transferencias.StatusRequerimentoRepository;
import br.com.unoesc.transferenciacompacito.repositorys.transferencias.TransferenciaRepository;
import br.com.unoesc.transferenciacompacito.repositorys.usuarios.UsuarioRepository;
import br.com.unoesc.transferenciacompacito.service.*;
import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/transferencias")
public class TransferenciaController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TransferenciaRepository transferenciaRepository;

    @Autowired
    private StatusRequerimentoRepository statusRequerimentoRepository;

    @Autowired
    private RestService restService;

    @Autowired
    private TransferenciaService transferenciaService;

    @PostMapping
    @Transactional(transactionManager="transferenciasTransactionManager", rollbackFor = Exception.class)
    public ResponseEntity<String> transeferirCoins(@RequestHeader("Authorization") String token,
        @Valid @RequestBody TransferenciaFORM body) throws EmailException
    {
        // valida token e ja retorna o usuario remetente
        int valor = body.getValor();
        String responseBody = restService.getRequest(body.getIdRemetente(), token);
        Usuario remetente = restService.getUsuarioByJson(responseBody);
        Optional<Usuario> opDestinatario = usuarioRepository.findById(body.getIdDestinatario());
        StatusRequerimento statusRequerimento = new StatusRequerimento(new Transferencia(body), StatusRequerimentosEnum.APROVADO);

        if (!opDestinatario.isPresent()) {
            throw new HttpClientErrorException(HttpStatus.CONFLICT); // Pelo ErroHandler irá passar a mensagem certa
        }

        // Executa a transferencia e insere um novo status de transferencia
        TransferenciaDto transferenciaDto = new TransferenciaDto(remetente, opDestinatario.get(), statusRequerimento);
        transferenciaService.transferir(transferenciaDto);
        transferenciaRepository.save(statusRequerimento.getIdTransferencia());
        statusRequerimentoRepository.save(statusRequerimento);

        new EmailService().emailTransferencia(transferenciaDto);
        return ResponseEntity.ok().body("Transferência efetuada com sucesso! Um e-mail foi enviado para os envolvidos.");
    }

    @GetMapping("/extrato/{id}")
    @Transactional(transactionManager="transferenciasTransactionManager", rollbackFor = Exception.class)
    public ResponseEntity extratoTransferencias(@RequestHeader("Authorization") String token, HttpServletRequest request, @PathVariable Long id) {
        String responseBody = restService.getRequest(id, token);
        Usuario usuarioSolicitante = restService.getUsuarioByJson(responseBody);

        ArrayList<ExtratoTransferenciaDto> extratoDtos = transferenciaService.getExtrato(usuarioSolicitante);

        return ResponseEntity.ok().body(extratoDtos);
    }
}


