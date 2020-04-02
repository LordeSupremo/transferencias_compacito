package br.com.unoesc.transferenciacompacito.swagger.configuracao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("br.com.unoesc.transferenciacompacito"))
            .build()
            .apiInfo(getApiInfo())
            .globalOperationParameters(Arrays.asList(new ParameterBuilder()
                .name("Authorization")
                .description("Header for token JWT")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false).build()
            ));
    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
            .title("Transferencia Compacito API")
            .version("1.0.1")
            .contact(new Contact(
                "Jhonatan Oliveira & Nicolas Maia",
                null,
                "jhonatan.oliveira_BO@compasso.com.br, nicolas.maia_BOLS@compasso.com.br"
            ))
            .build();
    }
}
