package br.com.portoseguro.alteracaodados.domain.vo;

import br.com.portoseguro.alteracaodados.domain.entity.Alteration;

import java.util.HashMap;
import java.util.Map;

public class FacialBiometricsState extends State {

    public FacialBiometricsState(Alteration alteration) {
        super(alteration, "facialBiometric");
    }

    @Override
    public OutputState initial(InputState inputState) {
        return null;
    }

    @Override
    public OutputState facialBiometric(InputState inputState) {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("score", "90");
        metadata.put("result", "PASSED");
        OutputState outputState = new OutputState();
        outputState.setState(this.value);
        outputState.setMetadata(metadata);
        return outputState;
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
