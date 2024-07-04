package br.com.portoseguro.alteracaodados.infrastructure.http;

import br.com.portoseguro.alteracaodados.application.AlterationService;
import br.com.portoseguro.alteracaodados.infrastructure.gateway.UserGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/alteration")
public class AlterationController {

    private final AlterationService alterationService;

    @Autowired
    public AlterationController(UserGateway userGateway) {
        this.alterationService = new AlterationService(userGateway);
    }

    @PostMapping("/process")
    public ResponseEntity<Map<String, Object>> process(@RequestBody Map<String, Object> request) {
        String cpf = (String) request.get("cpf");
        String token = (String) request.get("token");
        Map<String, Object> metadata = (Map<String, Object>) request.get("metadata");

        AlterationService.AlterationUseCaseInput input = new AlterationService.AlterationUseCaseInput(cpf, token, metadata);
        AlterationService.AlterationUseCaseOutput output = alterationService.execute(input);

        Map<String, Object> response = new HashMap<>();
        response.put("state", output.state());
        response.put("nextState", output.nextState());
        response.put("token", output.token());
        response.put("metadata", output.metadata());

        return ResponseEntity.ok(response);
    }
}