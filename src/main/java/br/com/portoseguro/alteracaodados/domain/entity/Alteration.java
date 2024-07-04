package br.com.portoseguro.alteracaodados.domain.entity;

import br.com.portoseguro.alteracaodados.domain.exceptions.ValidationError;
import br.com.portoseguro.alteracaodados.domain.vo.*;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Alteration {
    private final UUID id;
    private final User user;
    private State state;

    private Alteration(User user, String state) {
        this.id = UUID.randomUUID();
        this.user = user;
        this.state = StateFactory.create(this, state);
    }

    public static Alteration create(User user) {
        return new Alteration(user, "initial");
    }

    public static Alteration restore(User user, String state) {
        return new Alteration(user, state);
    }

    public OutputState execute(InputState inputState) {


        return switch (this.getState()) {
            case "initial" -> this.initial(inputState);
            case "facialBiometric" -> this.facialBiometric(inputState);
            case "authenticator" -> this.authenticator(inputState);
            case "changeData" -> this.changeData(inputState);
            default -> throw new ValidationError("State is not valid", -2);
        };
    }

    public void goToNextStep(){
        this.state = StateFactory.nextState(this);
    }

    public String getState() {
        return state.getValue();
    }

    public String getNextStage() {
        return state.getNextStage();
    }

    public String getToken() {
        return state.getToken().getValue();
    }

    public OutputState initial(InputState initialState) {
        return new InitialState(this).initial(initialState);
    }

    public OutputState facialBiometric(InputState initialState) {
        return new FacialBiometricsState(this).facialBiometric(initialState);
    }

    public OutputState authenticator(InputState initialState) {
        return new AuthenticatorState(this).authenticator(initialState);
    }

    public OutputState changeData(InputState initialState) {
        return new AlterationState(this).changeData(initialState);
    }
}