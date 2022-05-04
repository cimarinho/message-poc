package br.com.luizalabs.poc.message;

import br.com.luizalabs.poc.message.domain.Flow;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serde;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonSerde;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Serde<Flow> flowJsonSerde() {
        return new JsonSerde<>(Flow.class, new ObjectMapper());
    }

}
