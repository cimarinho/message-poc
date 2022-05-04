package br.com.luizalabs.poc.message.presentation.controller;


import br.com.luizalabs.poc.message.presentation.mapper.FlowMapper;
import br.com.luizalabs.poc.message.domain.Flow;
import br.com.luizalabs.poc.message.domain.FlowStatus;
import br.com.luizalabs.poc.message.application.FlowApplication;
import br.com.luizalabs.poc.message.presentation.dto.FlowRequest;
import br.com.luizalabs.poc.message.presentation.dto.FlowResponse;
import br.com.luizalabs.poc.message.presentation.dto.FlowStatusResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = FlowController.class)
public class FlowControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FlowApplication flowApplication;

    @MockBean
    private FlowMapper flowMapper;

    @Test
    public void new_flow() throws Exception {
        FlowResponse f = new FlowResponse();
        f.setStatus( FlowStatus.PENDING.toString());
        when(flowMapper.mapToFlow(any(FlowRequest.class), any(), any())).thenReturn(Flow.builder().name("name").build());
        when(flowApplication.createFlow()).thenReturn(value -> Flow.builder().name("name").build());
        when(flowMapper.mapToFlowRespose(any(Flow.class))).thenReturn(f);
        this.mockMvc.perform(post("/v1/flow").header("transactionId","1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json("{\"status\":\"PENDING\"}"));

    }

    @Test
    public void error_transactionId_new_flow() throws Exception {
        FlowResponse f = new FlowResponse();
        f.setStatus( FlowStatus.PENDING.toString());
        when(flowMapper.mapToFlow(any(FlowRequest.class), any(), any())).thenReturn(Flow.builder().name("name").build());
        when(flowApplication.createFlow()).thenReturn(value -> Flow.builder().name("name").build());
        when(flowMapper.mapToFlowRespose(any(Flow.class))).thenReturn(f);
        this.mockMvc.perform(post("/v1/flow")).andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void error_new_flow() throws Exception {
        this.mockMvc.perform(post("/v1/flow")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(new FlowRequest())))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void get_flow() throws Exception {
        FlowStatusResponse f = new FlowStatusResponse();
        f.setStatus( FlowStatus.PENDING.toString());
        when(flowApplication.getFlow()).thenReturn(value -> FlowStatus.PENDING);
        when(flowMapper.mapToFlowRespose(any(FlowStatus.class))).thenReturn(f);
        this.mockMvc.perform(get("/v1/flow/1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json("{\"status\":\"PENDING\"}"));

    }

    @Test
    public void error_get_flow() throws Exception {
        FlowStatusResponse f = new FlowStatusResponse();
        f.setStatus( FlowStatus.PENDING.toString());
        when(flowApplication.getFlow()).thenReturn(value -> FlowStatus.PENDING);
        when(flowMapper.mapToFlowRespose(any(FlowStatus.class))).thenReturn(f);
        this.mockMvc.perform(get("/v1/flow/1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json("{\"status\":\"PENDING\"}"));

    }
}
