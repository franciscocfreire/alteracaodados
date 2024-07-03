package br.com.portoseguro.alteracaodados.infrastructure.gateway;

import br.com.portoseguro.alteracaodados.domain.Entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserGatewayFakeImpl implements UserGateway {

    @Override
    public User restore(String cpf) {
        // Simula a restauração do usuário a partir de um repositório
        return new User("user123", cpf, "maskedemail@example.com", "maskedphone");
    }
}