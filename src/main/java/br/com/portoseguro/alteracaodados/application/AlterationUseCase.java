package br.com.portoseguro.alteracaodados.application;

import br.com.portoseguro.alteracaodados.domain.vo.InputState;
import br.com.portoseguro.alteracaodados.domain.vo.OutputState;
import br.com.portoseguro.alteracaodados.domain.entity.Alteration;
import br.com.portoseguro.alteracaodados.domain.entity.User;
import br.com.portoseguro.alteracaodados.domain.vo.PersistenceToken;
import br.com.portoseguro.alteracaodados.infrastructure.gateway.UserGateway;

import java.util.Map;

public class AlterationUseCase {

    private final UserGateway userGateway;

    public AlterationUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public AlterationUseCaseOutput execute(AlterationUseCaseInput alterationUseCaseInput) {

        User user = userGateway.restoreByCpf(alterationUseCaseInput.cpf);
        PersistenceToken persistenceToken;
        Alteration alteration;

        if (alterationUseCaseInput.token == null) {
            alteration = Alteration.create(user);
        } else {
            persistenceToken = PersistenceToken.restoreByToken(alterationUseCaseInput.token);
            alteration = Alteration.restore(user, persistenceToken.getCurrentState());
            alteration.goToNextStep();
        }

        InputState inputState = new InputState();
        inputState.setMetadata(alterationUseCaseInput.metadata);
        OutputState outputState = alteration.execute(inputState);

        return new AlterationUseCaseOutput(alteration.getState(), alteration.getNextStage(), alteration.getToken(), outputState.getMetadata());
    }

    public record AlterationUseCaseInput(String cpf, String token, Map<String, Object> metadata) {
    }

    public record AlterationUseCaseOutput(String state, String nextState, String token, Map<String, Object> metadata) {
    }
}