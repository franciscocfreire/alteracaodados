package br.com.portoseguro.alteracaodados.application;

import br.com.portoseguro.alteracaodados.domain.Entity.User;
import br.com.portoseguro.alteracaodados.domain.vo.StateFactory;
import br.com.portoseguro.alteracaodados.domain.vo.StateToken;
import br.com.portoseguro.alteracaodados.infrastructure.gateway.UserGateway;

import java.util.Map;

public class AlterationUseCase {

    private State currentState;
    private final UserGateway userGateway;

    public AlterationUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public AlterationUseCaseOutput execute(AlterationUseCaseInput alterationUseCaseInput) {

        User user = userGateway.restore(alterationUseCaseInput.cpf);
        StateToken stateToken;

        if (alterationUseCaseInput.token == null) {
            currentState = new InitialState(user);
            stateToken = StateToken.create();
        } else {
            stateToken = StateToken.restore(alterationUseCaseInput.token);
            currentState = StateFactory.create(stateToken, user);
        }

        InputState inputState = new InputState();
        inputState.setMetadata(alterationUseCaseInput.metadata);
        OutputState outputState = currentState.execute(inputState);
        StateToken nextStateToken = stateToken.nextState();

        return new AlterationUseCaseOutput(outputState.getState(), nextStateToken.currentState(), nextStateToken.getValue(), outputState.getMetadata());
    }

    public record AlterationUseCaseInput(String cpf, String token, Map<String, Object> metadata) {
    }

    public record AlterationUseCaseOutput(String state, String nextState, String token, Map<String, Object> metadata) {
    }
}