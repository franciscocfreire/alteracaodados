package br.com.portoseguro.alteracaodados.application;

import br.com.portoseguro.alteracaodados.domain.entity.Alteration;

import java.util.HashMap;
import java.util.Map;

public class AlterationState extends State {

    public AlterationState(Alteration alteration) {
        super(alteration, "changeData");
    }

    @Override
    public OutputState initial(InputState inputState) {
        return null;
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
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("message", "access data were change");
        metadata.put("result", "PASSED");
        OutputState outputState = new OutputState();
        outputState.setState(this.value);
        outputState.setMetadata(metadata);
        return outputState;
    }
}
