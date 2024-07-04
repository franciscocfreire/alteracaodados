package br.com.portoseguro.alteracaodados.domain.vo;

import br.com.portoseguro.alteracaodados.application.*;
import br.com.portoseguro.alteracaodados.domain.entity.Alteration;

public class StateFactory {
    public static State create(Alteration alteration, String state) {
        return switch (state) {
            case "initial" -> new InitialState(alteration);
            case "facialBiometric" -> new FacialBiometricsState(alteration);
            case "authenticator" -> new AuthenticatorState(alteration);
            case "changeData" -> new AlterationState(alteration);
            default -> throw new IllegalArgumentException("Unknown state: " + alteration.getState());
        };
    }

    public static State nextState(Alteration alteration) {
        return switch (alteration.getState()) {
            case "initial" -> new FacialBiometricsState(alteration);
            case "facialBiometric" -> new AuthenticatorState(alteration);
            case "authenticator" -> new AlterationState(alteration);
            case "changeData" -> throw new IllegalArgumentException("Invalide state: " + alteration.getState());
            default -> throw new IllegalArgumentException("Unknown state: " + alteration.getState());
        };
    }
}