package br.com.portoseguro.alteracaodados.domain.vo;

import br.com.portoseguro.alteracaodados.domain.entity.Alteration;
import br.com.portoseguro.alteracaodados.domain.exceptions.ValidationError;

import java.util.HashMap;
import java.util.Map;

public class InitialState extends State {

    public InitialState(Alteration alteration) {
        super(alteration, StateEnum.INITIAL);
    }

    @Override
    public OutputState initial(InputState inputState) {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("email", this.alteration.getUser().getMaskedEmail());
        metadata.put("phone", this.alteration.getUser().getMaskedPhone());
        return new OutputState(this.value.name(), metadata);
    }

    @Override
    public OutputState facialBiometric(InputState inputState) {
        throw new ValidationError("Invalid status", -11);
    }

    @Override
    public OutputState authenticator(InputState inputState) {
        throw new ValidationError("Invalid status", -11);
    }

    @Override
    public OutputState changeData(InputState inputState) {
        throw new ValidationError("Invalid status", -11);
    }
}