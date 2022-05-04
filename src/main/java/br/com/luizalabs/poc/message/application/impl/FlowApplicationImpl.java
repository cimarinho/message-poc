package br.com.luizalabs.poc.message.application.impl;

import br.com.luizalabs.poc.message.application.FlowApplication;
import br.com.luizalabs.poc.message.config.FlowNotFoundException;
import br.com.luizalabs.poc.message.domain.Flow;
import br.com.luizalabs.poc.message.domain.FlowStatus;
import br.com.luizalabs.poc.message.infraestructure.kafka.FlowKafkaInfraestructure;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class FlowApplicationImpl implements FlowApplication {

    private final FlowKafkaInfraestructure flowKafkaInfraestructure;

    @Override
    public Function<Flow, Flow> createFlow() {
        return flow -> flowKafkaInfraestructure.createFlow().apply(flow);
    }

    @Override
    public Function<String, FlowStatus> getFlow() {
        return flow -> {
            try {
                return flowKafkaInfraestructure.getFlow().apply(flow);
            } catch (FlowNotFoundException flowNotFoundException) {
               throw new FlowNotFoundException("Flow not foud");
            }
        };
    }
}
