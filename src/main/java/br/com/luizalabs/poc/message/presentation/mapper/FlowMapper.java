package br.com.luizalabs.poc.message.presentation.mapper;

import br.com.luizalabs.poc.message.domain.Flow;
import br.com.luizalabs.poc.message.domain.FlowStatus;
import br.com.luizalabs.poc.message.presentation.dto.FlowRequest;
import br.com.luizalabs.poc.message.presentation.dto.FlowResponse;
import br.com.luizalabs.poc.message.presentation.dto.FlowStatusResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class FlowMapper {

    public Flow mapToFlow(FlowRequest flowRequest, String transactionId, String source) {
        return Flow.builder()
                .idProduct(flowRequest.getIdProduct())
                .price(flowRequest.getPrice())
                .cpfCnpj(flowRequest.getCpfCnpj())
                .transactionId(transactionId)
                .status(FlowStatus.PENDING)
                .source(source)
                .transcationDate(LocalDateTime.now())
                .name(flowRequest.getName()).build();
    }

    public FlowResponse mapToFlowRespose(Flow flow) {
        FlowResponse flowResponse = new FlowResponse();
        flowResponse.idProduct(flow.getIdProduct());
        flowResponse.price(flow.getPrice());
        flowResponse.cpfCnpj(flow.getCpfCnpj());
        flowResponse.transactionId(flow.getTransactionId());
        flowResponse.status(flow.getStatus().toString());
        flowResponse.transcationDate(formatData(flow.getTranscationDate()));
        flowResponse.name(flow.getName());
        return flowResponse;
    }

    public FlowStatusResponse mapToFlowRespose(FlowStatus flowStatus) {
        FlowStatusResponse flowResponse = new FlowStatusResponse();
        flowResponse.status(flowStatus.toString());
        return flowResponse;
    }

    String formatData(LocalDateTime localDateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        return localDateTime.format(formatter);
    }

}
