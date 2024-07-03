package br.com.portoseguro.alteracaodados.infrastructure.gateway;

import br.com.portoseguro.alteracaodados.domain.Entity.User;

public interface UserGateway {

    User restore(String cpf);
}
