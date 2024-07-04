package br.com.portoseguro.alteracaodados.domain.vo;

import br.com.portoseguro.alteracaodados.domain.entity.Alteration;
import br.com.portoseguro.alteracaodados.domain.exceptions.ValidationError;

import java.util.HashMap;
import java.util.Map;

public class AuthenticatorState extends State {

    public AuthenticatorState(Alteration alteration) {
        super(alteration, "authenticator");
    }

    @Override
    public OutputState authenticator(InputState inputState) {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("message", "Token Verified");
        metadata.put("result", "PASSED");
        return new OutputState(this.value, metadata);
    }

    @Override
    public OutputState initial(InputState inputState) {
        throw new ValidationError("Invalid status", -11);
    }

    @Override
    public OutputState facialBiometric(InputState inputState) {
        throw new ValidationError("Invalid status", -11);
    }


    @Override
    public OutputState changeData(InputState inputState) {
        throw new ValidationError("Invalid status", -11);

    }
}
