package br.com.unoesc.transferenciacompacito.service;

import br.com.unoesc.transferenciacompacito.models.usuarios.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class RestService {

    @Value("${url.api.usuarios}")
    private String usuariosUrl;

    private final RestTemplate restTemplate;

    public RestService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public String getRequest(Long id, String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", accessToken);

        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<String> response = restTemplate.exchange(usuariosUrl+id, HttpMethod.GET, entity, String.class);

        return response.getBody();
    }

    public Map getMapByJson(String json) {
        try{
            ObjectMapper objectMapper = new ObjectMapper();

            Map<?, ?> map = objectMapper.readValue(json, Map.class);

            // Apenas para facilitar a visualização das chaves e ver o valor que devem ter
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                System.out.println(entry.getKey() + "=" + entry.getValue());
            }

            return map;
        }catch (Exception e){
            return null;
        }
    }

    public Usuario getUsuarioByJson(String json) {
        try{
            Map<String, ?> usuarioMap = getMapByJson(json);
            return new Usuario(
                Long.parseLong(String.valueOf(usuarioMap.get("id"))),
                String.valueOf(usuarioMap.get("nome")),
                String.valueOf(usuarioMap.get("email")),
                Integer.parseInt(String.valueOf(usuarioMap.get("credito")))
            );
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }


}