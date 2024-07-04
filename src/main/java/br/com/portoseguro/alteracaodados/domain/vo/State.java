package br.com.portoseguro.alteracaodados.domain.vo;

import br.com.portoseguro.alteracaodados.domain.entity.Alteration;
import lombok.Getter;

import java.util.Date;
import java.util.Map;

public abstract class State {


    StateEnum value;
    Alteration alteration;
    @Getter
    PersistenceToken token;

    State(Alteration alteration, StateEnum value) {
        this.alteration = alteration;
        this.value = value;
        this.token = PersistenceToken.restore(alteration.getUser().getCpf(), this.value.name(), this.value.nextState().name(), new Date());
    }


    public String getValue() {
        return this.value.name();
    }

    public StateEnum getValueEnum() {
        return this.value;
    }

    public abstract OutputState initial(InputState inputState);

    public abstract OutputState facialBiometric(InputState inputState);

    public abstract OutputState authenticator(InputState inputState);

    public abstract OutputState changeData(InputState inputState);

    public record InputState(String token, Map<String, Object> metadata) {
    }

    public record OutputState(String state, Map<String, Object> metadata) {
    }
}