package br.com.portoseguro.alteracaodados;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/alteration")
public class AlterationController {

    private final AlterationUseCase alterationUseCase;

    @Autowired
    public AlterationController(UserGateway userGateway) {
        this.alterationUseCase = new AlterationUseCase(userGateway);
    }

    @PostMapping("/process")
    public ResponseEntity<Map<String, Object>> process(@RequestBody Map<String, Object> request) {
        String cpf = (String) request.get("cpf");
        String token = (String) request.get("token");
        Map<String, Object> metadata = (Map<String, Object>) request.get("metadata");

        AlterationUseCase.AlterationUseCaseInput input = new AlterationUseCase.AlterationUseCaseInput(cpf, token, metadata);
        AlterationUseCase.AlterationUseCaseOutput output = alterationUseCase.execute(input);

        Map<String, Object> response = new HashMap<>();
        response.put("state", output.state());
        response.put("token", output.token());
        response.put("metadata", output.metadata());

        return ResponseEntity.ok(response);
    }
}