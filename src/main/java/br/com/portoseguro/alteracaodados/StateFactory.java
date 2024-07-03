package br.com.portoseguro.alteracaodados;

public class StateFactory {
    public static State create(StateToken stateToken, User user) {
        return switch (stateToken.currentState()) {
            case "initial" -> new InitialState(user);
            case "biometric" -> new BiometricState(user);
            default -> throw new IllegalArgumentException("Unknown state: " + stateToken.currentState());
        };
    }
}