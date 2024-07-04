package br.com.portoseguro.alteracaodados.application;

import br.com.portoseguro.alteracaodados.domain.entity.Alteration;
import br.com.portoseguro.alteracaodados.domain.entity.User;
import br.com.portoseguro.alteracaodados.domain.vo.State;
import br.com.portoseguro.alteracaodados.infrastructure.gateway.UserGateway;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class AlterationUseCase {

    private final UserGateway userGateway;

    public AlterationUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public AlterationUseCaseOutput execute(AlterationUseCaseInput alterationUseCaseInput) {

        User user = userGateway.restoreByCpf(alterationUseCaseInput.cpf);
        Alteration alteration = Alteration.createOrRestore(user, alterationUseCaseInput.token);
        State.InputState inputState = new State.InputState(null, alterationUseCaseInput.metadata);
        State.OutputState outputState = alteration.execute(inputState);
        return new AlterationUseCaseOutput(alteration.getState().name(), alteration.getNextStage(), alteration.getToken(), outputState.metadata());
    }

    public record AlterationUseCaseInput(String cpf, String token, Map<String, Object> metadata) {
    }

    public record AlterationUseCaseOutput(String state, String nextState, String token, Map<String, Object> metadata) {
    }
}