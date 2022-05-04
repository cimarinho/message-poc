package br.com.luizalabs.poc.message.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@ToString
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Flow implements Serializable {

    public static final String FLOW_INITIAL = "FLOW_INITIAL";

    private String name;
    private String cpfCnpj;
    private Integer idProduct;
    private BigDecimal price;
    private String transactionId;
    private FlowStatus status;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime transcationDate;

    @JsonIgnore
    private String source;

}
