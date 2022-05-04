package br.com.luizalabs.poc.message.infraestructure;

import br.com.luizalabs.poc.message.domain.Flow;
import br.com.luizalabs.poc.message.domain.FlowStatus;

import java.util.function.Function;

public interface FlowInfraestructure {

    Function<Flow, Flow> createFlow();
    Function<String, FlowStatus> getFlow();

}
