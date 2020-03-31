package br.com.unoesc.transferenciacompacito.swagger.configuracao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("br.com.unoesc.transferenciacompacito"))
            .build()
            .apiInfo(getApiInfo())
            .ignoredParameterTypes(ResponseEntity.class)
            .globalOperationParameters(Arrays.asList(new ParameterBuilder()
                .name("Authorization")
                .description("Header for token JWT")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false).build()
            ));
    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder().title("Transferencia Compacito API").build();
    }
}
