package com.forzzle.hodeum.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Value("${server.domain}")
    private String serverDomain;

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
            .version("1.0")
            .title("Hodeum")
            .description("Hodeum API");

        Server server = new Server();
        server.setUrl(serverDomain); // https://에 접근 가능하게 설정

        return new OpenAPI()
            .info(info)
            .servers(List.of(server));
    }
}
