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

    private Alteration(User user, StateEnum state) {
        this.id = UUID.randomUUID();
        this.user = user;
        this.state = StateFactory.create(this, state);
    }

    public static Alteration create(User user) {
        return new Alteration(user, StateEnum.INITIAL);
    }

    public static Alteration restore(User user, StateEnum state) {
        return new Alteration(user, state);
    }

    public State.OutputState execute(State.InputState inputState) {
        return switch (this.getState()) {
            case INITIAL -> this.initial(inputState);
            case FACIAL_BIOMETRIC -> this.facialBiometric(inputState);
            case AUTHENTICATOR -> this.authenticator(inputState);
            case CHANGE_DATA -> this.changeData(inputState);
            default -> throw new ValidationError("State is not valid", -2);
        };
    }

    public void goToNextStep(){
        this.state = StateFactory.create(this, this.state.getValueEnum().nextState());
    }

    public StateEnum getState() {
        return StateEnum.valueOf(state.getValue());
    }

    public String getNextStage() {
        return state.getValueEnum().nextState().name();
    }

    public String getToken() {
        if (this.state.getValueEnum().equals(StateEnum.CHANGE_DATA)) return null;
        return state.getToken().getValue();
    }

    public State.OutputState initial(State.InputState initialState) {
        return new InitialState(this).initial(initialState);
    }

    public State.OutputState facialBiometric(State.InputState initialState) {
        return new FacialBiometricsState(this).facialBiometric(initialState);
    }

    public State.OutputState authenticator(State.InputState initialState) {
        return new AuthenticatorState(this).authenticator(initialState);
    }

    public State.OutputState changeData(State.InputState initialState) {
        return new AlterationState(this).changeData(initialState);
    }
}