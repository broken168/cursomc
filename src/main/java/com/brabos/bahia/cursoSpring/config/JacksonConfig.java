package com.brabos.bahia.cursoSpring.config;

import com.brabos.bahia.cursoSpring.domain.PaymentWithBoleto;
import com.brabos.bahia.cursoSpring.domain.PaymentWithCard;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonConfig {

    @Bean
    public Jackson2ObjectMapperBuilder objectMapperBuilder () {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder(){
            public void configure(ObjectMapper objectMapper) {
                objectMapper.registerSubtypes(PaymentWithCard.class);
                objectMapper.registerSubtypes(PaymentWithBoleto.class);
                super.configure(objectMapper);
            }
        };
        return builder;
    }
}
