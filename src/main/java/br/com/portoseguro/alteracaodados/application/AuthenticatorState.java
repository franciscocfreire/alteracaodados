package br.com.portoseguro.alteracaodados.application;

import br.com.portoseguro.alteracaodados.domain.entity.Alteration;
import br.com.portoseguro.alteracaodados.domain.exceptions.ValidationError;
import br.com.portoseguro.alteracaodados.domain.vo.State;
import br.com.portoseguro.alteracaodados.domain.vo.StateEnum;

import java.util.HashMap;
import java.util.Map;

public class AuthenticatorState extends State {

    public AuthenticatorState(Alteration alteration) {
        super(alteration, StateEnum.AUTHENTICATOR);
    }

    @Override
    public OutputState authenticator(InputState inputState) {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("message", "Token Verified");
        metadata.put("result", "PASSED");
        return new OutputState(this.getValueEnum().name(), metadata);
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
