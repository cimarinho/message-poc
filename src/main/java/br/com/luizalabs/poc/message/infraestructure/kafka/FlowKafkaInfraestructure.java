package br.com.luizalabs.poc.message.infraestructure.kafka;

import br.com.luizalabs.poc.message.config.FlowNotFoundException;
import br.com.luizalabs.poc.message.domain.Flow;
import br.com.luizalabs.poc.message.domain.FlowStatus;
import br.com.luizalabs.poc.message.infraestructure.FlowInfraestructure;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class FlowKafkaInfraestructure implements FlowInfraestructure {

    private final InteractiveQueryService interactiveQueryService;
    private final Serde<Flow> flowSerde;

    @Value("${spring.cloud.stream.kafka.streams.binder.brokers}")
    private String bootstrapServer;

    @Value("${spring.cloud.stream.bindings.flow-in-0.destination}")
    private String topic;

    @Value("${flow}")
    private String eventFlow ;


    BiFunction<Serde<Flow>, String, DefaultKafkaProducerFactory<String, Flow>> flowJsonSerdeFactoryFunction
            = (serde, bootstrapServer) -> new DefaultKafkaProducerFactory<>(Map.of(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer,
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, serde.serializer().getClass()));

    @Override
    public Function<Flow, Flow> createFlow() {
        return flow -> {
            new KafkaTemplate<>(flowJsonSerdeFactoryFunction.apply(flowSerde, bootstrapServer), true) {{
                setDefaultTopic(topic);
                sendDefault(flow.getTransactionId(), flow);
            }};
            return flow;
        };
    }

    @Override
    public Function<String, FlowStatus> getFlow() {
        return flow -> {
            final ReadOnlyKeyValueStore<String, String> store =
                    interactiveQueryService.getQueryableStore(eventFlow, QueryableStoreTypes.keyValueStore());
            return FlowStatus.valueOf(Optional.ofNullable(store.get(flow))
                    .orElseThrow(() -> new FlowNotFoundException("Flow not found")));
        };
    }
}
