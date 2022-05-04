package br.com.luizalabs.poc.message.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum FlowStatus {
    PENDING("PENDING"),

    FLOW_CREATED("FLOW_CREATED");

    private final String name;

    public FlowStatus equalsName(String otherName) {
        return FlowStatus.valueOf(otherName);
    }

    public String toString() {
        return this.name;
    }
}
