package br.com.luizalabs.poc.message.application.impl;


import br.com.luizalabs.poc.message.config.FlowNotFoundException;
import br.com.luizalabs.poc.message.domain.Flow;
import br.com.luizalabs.poc.message.domain.FlowStatus;
import br.com.luizalabs.poc.message.infraestructure.kafka.FlowKafkaInfraestructure;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
@SpringBootTest
public class FlowApplicationImplTest {

    @Autowired
    private FlowApplicationImpl flowApplication;

    @MockBean
    private FlowKafkaInfraestructure flowKafkaInfraestructure;


    @Test
    public void create_flow() {
        when(flowKafkaInfraestructure.createFlow()).thenReturn(value -> Flow.builder().name("name").build());
        Flow apply = flowApplication.createFlow().apply(Flow.builder().build());
        assertEquals(apply.getName(), "name");
    }

    @Test
    public void get_flow() {
        when(flowKafkaInfraestructure.getFlow()).thenReturn(value -> FlowStatus.PENDING);
        FlowStatus apply = flowApplication.getFlow().apply("PENDING");
        assertEquals(FlowStatus.PENDING, apply);
    }

    @Test
    public void get_flow_exception() {
        when(flowKafkaInfraestructure.getFlow()).thenThrow(new FlowNotFoundException("Flow not foud"));
        assertThrows(FlowNotFoundException.class, () ->  flowApplication.getFlow().apply("PENDING"));
    }


}
