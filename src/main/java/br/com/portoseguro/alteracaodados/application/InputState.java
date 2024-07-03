package br.com.portoseguro.alteracaodados.application;

import lombok.Data;

import java.util.Map;

@Data
public class InputState {
    private String token;
    private Map<String, Object> metadata;
}
