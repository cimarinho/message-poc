package br.com.luizalabs.poc.message.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class FlowNotFoundException extends RuntimeException {
    public FlowNotFoundException(String msg) {
        super(msg);
    }
}
