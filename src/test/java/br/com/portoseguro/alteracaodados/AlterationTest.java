package br.com.portoseguro.alteracaodados;

import br.com.portoseguro.alteracaodados.domain.entity.User;
import br.com.portoseguro.alteracaodados.application.AlterationService;
import br.com.portoseguro.alteracaodados.domain.vo.StateToken;
import br.com.portoseguro.alteracaodados.infrastructure.gateway.UserGateway;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.crypto.SecretKey;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AlterationTest {

    private static final String SECRET_KEY = "MjZmNDJlYjQtMDYzZS00ZjU4LWE0NmQtOGJlMWIwMjExZDM4Cg==";

    private AlterationService alterationService;
    @Mock
    private UserGateway userGateway;
    private User userMock;

    @BeforeEach
    void setUp() {
        SecretKey key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(SECRET_KEY));
        StateToken.setSecretKey(key);

        userMock = new User("user123", "33998291830", "maskemail@gmail.com", "maskedphone");
        alterationService = new AlterationService(userGateway);
    }

    @Test
    @DisplayName("Deve testar o estado incial da alteração de dados")
    public void deveTestarEstadoInicialAlteracaoDados() {
        String expectedCpf = "33998291830";
        String expectedEmail = "maskemail@gmail.com";
        String expectedPhone = "maskedphone";
        String expectedNextState = "facialBiometric";
        AlterationService.AlterationUseCaseInput alterationUseCaseInput = new AlterationService.AlterationUseCaseInput(expectedCpf, null, null);
        when(userGateway.restoreByCpf(eq(expectedCpf))).thenReturn(userMock);
        var result = alterationService.execute(alterationUseCaseInput);
        assertEquals("initial", result.state());
        assertNotNull(result.token());
        assertEquals(expectedEmail, result.metadata().get("email"));
        assertEquals(expectedPhone, result.metadata().get("phone"));
        assertEquals(expectedNextState, result.nextState());
    }

    @Test
    @DisplayName("Deve testar o estado biometria da alteração de dados")
    public void deveTestarEstadoBiometriaAlteracaoDados() {
        String expectedCpf = "33998291830";
        String expectedScore = "90";
        String expectedResult = "PASSED";
        AlterationService.AlterationUseCaseInput alterationUseCaseInput = new AlterationService.AlterationUseCaseInput(expectedCpf, null, null);
        when(userGateway.restoreByCpf(eq(expectedCpf))).thenReturn(userMock);
        var resultInitial = alterationService.execute(alterationUseCaseInput);

        AlterationService.AlterationUseCaseInput alterationUseCaseInputSecondTime = new AlterationService.AlterationUseCaseInput(expectedCpf, resultInitial.token(), null);
        var resultSecondTime = alterationService.execute(alterationUseCaseInputSecondTime);

        assertEquals("facialBiometric", resultSecondTime.state());
        assertNotNull(resultSecondTime.token());
        assertEquals(expectedScore, resultSecondTime.metadata().get("score"));
        assertEquals(expectedResult, resultSecondTime.metadata().get("result"));
    }


}
