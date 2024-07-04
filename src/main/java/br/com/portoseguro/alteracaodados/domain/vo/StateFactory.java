package br.com.portoseguro.alteracaodados.domain.vo;

import br.com.portoseguro.alteracaodados.application.ChangeAccessDataState;
import br.com.portoseguro.alteracaodados.application.AuthenticatorState;
import br.com.portoseguro.alteracaodados.application.FacialBiometricsState;
import br.com.portoseguro.alteracaodados.application.InitialState;
import br.com.portoseguro.alteracaodados.domain.entity.Alteration;

public class StateFactory {
    public static State create(Alteration alteration, StateEnum state) {
        return switch (state) {
            case INITIAL -> new InitialState(alteration);
            case FACIAL_BIOMETRIC -> new FacialBiometricsState(alteration);
            case AUTHENTICATOR -> new AuthenticatorState(alteration);
            case CHANGE_DATA -> new ChangeAccessDataState(alteration);
            default -> throw new IllegalArgumentException("Unknown state: " + alteration.getState());
        };
    }
}