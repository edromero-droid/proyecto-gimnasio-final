package com.gimnasio.socios;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuración general del microservicio.
 * @Bean le dice a Spring que guarde este objeto para poder usarlo en cualquier clase.
 * RestTemplate es el cliente HTTP que usamos para llamar a otros microservicios.
 */
@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
