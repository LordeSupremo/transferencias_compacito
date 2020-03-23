package br.com.unoesc.transferenciacompacito.configuracao;

import br.com.unoesc.transferenciacompacito.models.transferencias.dto.ErroDTO;
import br.com.unoesc.transferenciacompacito.service.RestService;
import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import javax.mail.internet.AddressException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ErroDeValidacaoHandler {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private RestService restService;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErroDTO> handle(MethodArgumentNotValidException exception) {

        List<ErroDTO> erroDTO = new ArrayList<>();
        List<FieldError> fieldError = exception.getBindingResult().getFieldErrors();

        fieldError.forEach(e -> {
            String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            ErroDTO erro = new ErroDTO(e.getField(), mensagem);
            erroDTO.add(erro);
        });

        return erroDTO;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmailException.class)
    public ErroDTO exception(AddressException exception) {
        return  new ErroDTO(400, "Transação realizada com sucesso! erro ao enviar email para " + exception.getRef());
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpClientErrorException.class)
    public String exception(HttpClientErrorException exception) {
        if (exception.getStatusCode() == HttpStatus.NOT_FOUND){
            return "{404: Usuário remetente não encontrado!}";
        }

        if (exception.getStatusCode() == HttpStatus.CONFLICT){
            return "{404: Usuário destinatário não encontrado!}";
        }

        return "{" + exception.getMessage() + "}";
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ResourceAccessException.class)
    public ErroDTO exception(ResourceAccessException exception) {
        return new ErroDTO(400, "Erro ao conectar com a api de usuários.");
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ErroDTO exception(Exception exception) {
        return new ErroDTO(400, exception.getMessage());
    }
}
