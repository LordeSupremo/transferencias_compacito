package br.com.unoesc.transferenciacompacito.service;

import br.com.unoesc.transferenciacompacito.models.transferencias.StatusRequerimento;
import br.com.unoesc.transferenciacompacito.models.transferencias.Transferencia;
import br.com.unoesc.transferenciacompacito.models.transferencias.dto.ExtratoTransferenciaDto;
import br.com.unoesc.transferenciacompacito.models.transferencias.dto.TransferenciaDto;
import br.com.unoesc.transferenciacompacito.models.usuarios.Usuario;
import br.com.unoesc.transferenciacompacito.repositorys.transferencias.StatusRequerimentoRepository;
import br.com.unoesc.transferenciacompacito.repositorys.transferencias.TransferenciaRepository;
import br.com.unoesc.transferenciacompacito.repositorys.usuarios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TransferenciaService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    private TransferenciaRepository transferenciaRepository;

    @Autowired
    private StatusRequerimentoRepository statusRequerimentoRepository;

    public Boolean transferir(Usuario remetente, Usuario destinatario, Integer valor){
        try{
            remetente.sacar(valor);
            destinatario.depositar(valor);

            usuarioRepository.save(remetente);
            usuarioRepository.save(destinatario);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public Boolean transferir(TransferenciaDto transferenciaDto){
        try{
            int valor = transferenciaDto.getIdTransferencia().getValor();
            transferenciaDto.getIdUsuarioOrigem().sacar(valor);
            transferenciaDto.getIdUsuarioDestino().depositar(valor);

            usuarioRepository.save(transferenciaDto.getIdUsuarioOrigem());
            usuarioRepository.save(transferenciaDto.getIdUsuarioDestino());
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public ArrayList<ExtratoTransferenciaDto> getExtrato(Usuario usuarioSolicitante){
        ArrayList<ExtratoTransferenciaDto> extratoDtos = new ArrayList<>();

        ArrayList<Transferencia> transferencias = transferenciaRepository.findAllByIdUsuarioOrigem(usuarioSolicitante.getId());

        List<Long> transferenciasID = new ArrayList();
        List<Long> usuariosID = new ArrayList();

        transferencias.forEach(transferencia -> {
            transferenciasID.add(transferencia.getId());
            usuariosID.add(transferencia.getIdUsuarioDestino());
        });

        ArrayList<StatusRequerimento> statusRequerimentos = statusRequerimentoRepository.findAllByIdTransferencia(transferenciasID);
        ArrayList<Usuario> usuarios = usuarioRepository.findAllById(usuariosID);
        Map<Long, Usuario> usuarioMap = new HashMap<>();

        usuarios.forEach(usuario -> {
            usuarioMap.put(usuario.getId(), usuario);
        });


        statusRequerimentos.forEach(status ->{
            extratoDtos.add(new ExtratoTransferenciaDto(
                usuarioSolicitante, usuarioMap.get(status.getIdTransferencia().getIdUsuarioDestino()), status
            ));
        });

        return extratoDtos;
    }
}
