package br.com.portoseguro.alteracaodados.domain.vo;

import br.com.portoseguro.alteracaodados.domain.entity.Alteration;
import br.com.portoseguro.alteracaodados.domain.exceptions.ValidationError;
import lombok.Getter;

import java.util.Date;

public abstract class State {

    @Getter
    String value;
    Alteration alteration;
    @Getter
    PersistenceToken token;

    State(Alteration alteration, String value) {
        this.alteration = alteration;
        this.value = value;
        this.token = PersistenceToken.restore(this.value, getNextStage(), new Date());
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