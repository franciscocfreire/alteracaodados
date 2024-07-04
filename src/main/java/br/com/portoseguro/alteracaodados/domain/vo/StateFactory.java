package br.com.portoseguro.alteracaodados.domain.vo;

import br.com.portoseguro.alteracaodados.domain.entity.Alteration;

public class StateFactory {
    public static State create(Alteration alteration, StateEnum state) {
        return switch (state) {
            case INITIAL -> new InitialState(alteration);
            case FACIAL_BIOMETRIC -> new FacialBiometricsState(alteration);
            case AUTHENTICATOR -> new AuthenticatorState(alteration);
            case CHANGE_DATA -> new AlterationState(alteration);
            default -> throw new IllegalArgumentException("Unknown state: " + alteration.getState());
        };
    }
}