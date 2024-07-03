package br.com.portoseguro.alteracaodados;

import java.util.HashMap;
import java.util.Map;

public class BiometricState implements State{

    private final User user;
    private final String STATE_NAME = "BIOMETRIC";

    public BiometricState(User user) {
        this.user = user;
    }

    @Override
    public OutputState execute(InputState inputState) {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("score", "90");
        metadata.put("result", "PASSED");
        OutputState outputState = new OutputState();
        outputState.setState(STATE_NAME);
        outputState.setMetadata(metadata);
        return outputState;
    }
}
