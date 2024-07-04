package br.com.portoseguro.alteracaodados.application;

import br.com.portoseguro.alteracaodados.domain.entity.Alteration;
import br.com.portoseguro.alteracaodados.domain.exceptions.ValidationError;
import br.com.portoseguro.alteracaodados.domain.vo.StateToken;
import lombok.Getter;

public abstract class State {

    @Getter
    String value;
    Alteration alteration;
    @Getter
    StateToken token;


    State(Alteration alteration, String value) {
        this.alteration = alteration;
        this.value = value;
        this.token = StateToken.restoreByState(this.value);
    }

    public String getNextStage() {
        return switch (value) {
            case "initial" -> "facialBiometric";
            case "facialBiometric" -> "authenticator";
            case "authenticator" -> "changeData";
            case "changeData" -> null;
            default -> throw new ValidationError("State is not valid", -2);
        };
    }

    public abstract OutputState initial(InputState inputState);
    public abstract OutputState facialBiometric(InputState inputState);
    public abstract OutputState authenticator(InputState inputState);
    public abstract OutputState changeData(InputState inputState);
}