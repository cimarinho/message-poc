package br.com.luizalabs.poc.message.presentation.processor;

import br.com.luizalabs.poc.message.domain.Flow;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.function.Function;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class FlowKafkaProcessor {

    private final Serde<Flow> flowSerde;


    @Value("${flow}")
    private String eventFlow;

    Function<KStream<String, Flow>, KTable<String, String>> kStreamKTableStringFunction = input -> input
            .groupBy((s, flow) -> flow.getTransactionId(),
                    Grouped.with(null, new JsonSerde<>(Flow.class, new ObjectMapper())))
            .aggregate(
                    String::new,
                    (s, flow, oldStatus) -> flow.getStatus().toString(),
                    Materialized.<String, String, KeyValueStore<Bytes, byte[]>>as(eventFlow)
                            .withKeySerde(Serdes.String()).
                            withValueSerde(Serdes.String())
            );

    @Bean
    public Function<KStream<String, Flow>, KStream<String, Flow>> flow() {
        return flowKStream -> {
            KTable<String, String> uuidStringKTable = kStreamKTableStringFunction.apply(flowKStream);
            return flowKStream
                    .peek((k, v) -> System.out.println(k + "=kstream=" + v))
                    .leftJoin(uuidStringKTable, (flow, status) -> flow, Joined.with(Serdes.String(), flowSerde, Serdes.String()));
        };
    }


    @KafkaListener(topics = "produtor-process-topic", groupId = "teste")
    public void receive(@Payload String message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info(String.format("topic %s and message %s!", topic, message));
    }

}
