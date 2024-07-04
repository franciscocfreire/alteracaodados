package br.com.portoseguro.alteracaodados.infrastructure.gateway;

import br.com.portoseguro.alteracaodados.domain.entity.User;

public interface UserGateway {

    User restoreByCpf(String cpf);
}
