package br.com.portoseguro.alteracaodados.application;

import lombok.Data;

import java.util.Map;

@Data
public class OutputState {
    private String state;
    private Map<String, Object> metadata;
}
