package br.com.portoseguro.alteracaodados.domain.vo;

import br.com.portoseguro.alteracaodados.application.State;
import br.com.portoseguro.alteracaodados.domain.Entity.User;
import br.com.portoseguro.alteracaodados.application.AlterationState;
import br.com.portoseguro.alteracaodados.application.AuthenticatorState;
import br.com.portoseguro.alteracaodados.application.FacialBiometricsState;
import br.com.portoseguro.alteracaodados.application.InitialState;

public class StateFactory {
    public static State create(StateToken stateToken, User user) {
        return switch (stateToken.currentState()) {
            case "initial" -> new InitialState(user);
            case "biometric" -> new FacialBiometricsState(user);
            case "authenticator" -> new AuthenticatorState(user);
            case "alteration" -> new AlterationState(user);
            default -> throw new IllegalArgumentException("Unknown state: " + stateToken.currentState());
        };
    }
}