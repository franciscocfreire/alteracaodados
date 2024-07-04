package br.com.portoseguro.alteracaodados.domain.vo;

import br.com.portoseguro.alteracaodados.domain.entity.Alteration;

import java.util.HashMap;
import java.util.Map;

public class InitialState extends State {


    public InitialState(Alteration alteration) {
        super(alteration, "initial");
    }

    @Override
    public OutputState initial(InputState inputState) {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("email", this.alteration.getUser().getMaskedEmail());
        metadata.put("phone", this.alteration.getUser().getMaskedPhone());
        OutputState outputState = new OutputState();
        outputState.setState(this.value);
        outputState.setMetadata(metadata);
        return outputState;
    }

    @Override
    public OutputState facialBiometric(InputState inputState) {
        return null;
    }

    @Override
    public OutputState authenticator(InputState inputState) {
        return null;
    }

    @Override
    public OutputState changeData(InputState inputState) {
        return null;
    }


}