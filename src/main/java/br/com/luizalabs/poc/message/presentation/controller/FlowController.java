package br.com.luizalabs.poc.message.presentation.controller;

import br.com.luizalabs.poc.message.application.FlowApplication;
import br.com.luizalabs.poc.message.domain.Flow;
import br.com.luizalabs.poc.message.domain.FlowStatus;
import br.com.luizalabs.poc.message.presentation.V1Api;
import br.com.luizalabs.poc.message.presentation.dto.FlowRequest;
import br.com.luizalabs.poc.message.presentation.dto.FlowResponse;
import br.com.luizalabs.poc.message.presentation.dto.FlowStatusResponse;
import br.com.luizalabs.poc.message.presentation.mapper.FlowMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class FlowController implements V1Api {

    private final FlowApplication flowApplication;
    private final FlowMapper flowMapper;

    @PostMapping("/v1/flow")
    public ResponseEntity<FlowResponse> newFlow(String transactionId, FlowRequest flowRequest) {
        Flow flow = flowApplication.createFlow().apply(flowMapper.mapToFlow(flowRequest, transactionId, Flow.FLOW_INITIAL));
        return ResponseEntity.ok(flowMapper.mapToFlowRespose(flow));
    }

    @GetMapping("/v1/flow/{transactionId}")
    public ResponseEntity<FlowStatusResponse> getFlow(String transactionId) {
        FlowStatus flow = flowApplication.getFlow().apply(transactionId);
        return ResponseEntity.ok(flowMapper.mapToFlowRespose(flow));
    }
}
