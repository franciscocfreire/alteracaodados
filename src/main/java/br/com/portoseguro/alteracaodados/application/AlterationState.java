package br.com.portoseguro.alteracaodados.application;

import br.com.portoseguro.alteracaodados.domain.Entity.User;

import java.util.HashMap;
import java.util.Map;

public class AlterationState implements State {

    private final User user;
    private final String STATE_NAME = "ALTERATION";

    public AlterationState(User user) {
        this.user = user;
    }

    @Override
    public OutputState execute(InputState inputState) {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("message", "access data were change");
        metadata.put("result", "PASSED");
        OutputState outputState = new OutputState();
        outputState.setState(STATE_NAME);
        outputState.setMetadata(metadata);
        return outputState;
    }
}
