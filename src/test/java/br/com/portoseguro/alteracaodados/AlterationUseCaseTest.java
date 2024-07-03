package br.com.portoseguro.alteracaodados;

import br.com.portoseguro.alteracaodados.application.AlterationUseCase;
import br.com.portoseguro.alteracaodados.domain.Entity.User;
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

import static br.com.portoseguro.alteracaodados.application.AlterationUseCase.AlterationUseCaseInput;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AlterationUseCaseTest {

    private static final String SECRET_KEY = "MjZmNDJlYjQtMDYzZS00ZjU4LWE0NmQtOGJlMWIwMjExZDM4Cg==";

    private AlterationUseCase alterationUseCase;
    @Mock
    private UserGateway userGateway;
    private User userMock;

    @BeforeEach
    void setUp() {
        SecretKey key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(SECRET_KEY));
        StateToken.setSecretKey(key);

        userMock = new User("user123", "33998291830", "maskemail@gmail.com", "maskedphone");
        alterationUseCase = new AlterationUseCase(userGateway);
    }

    @Test
    @DisplayName("Deve testar o estado incial da alteração de dados")
    public void deveTestarEstadoInicialAlteracaoDados() {
        String expectedCpf = "33998291830";
        String expectedEmail = "maskemail@gmail.com";
        String expectedPhone = "maskedphone";
        AlterationUseCaseInput alterationUseCaseInput = new AlterationUseCaseInput(expectedCpf, null,null);
        when(userGateway.restore(eq(expectedCpf))).thenReturn(userMock);
        var result = alterationUseCase.execute(alterationUseCaseInput);
        assertEquals("INITIAL", result.state());
        assertNotNull(result.token());
        assertEquals(expectedEmail, result.metadata().get("email"));
        assertEquals(expectedPhone, result.metadata().get("phone"));
    }

    @Test
    @DisplayName("Deve testar o estado biometria da alteração de dados")
    public void deveTestarEstadoBiometriaAlteracaoDados() {
        String expectedCpf = "33998291830";
        String expectedScore = "90";
        String expectedResult = "PASSED";
        AlterationUseCaseInput alterationUseCaseInput = new AlterationUseCaseInput(expectedCpf, null,null);
        when(userGateway.restore(eq(expectedCpf))).thenReturn(userMock);
        var resultInitial = alterationUseCase.execute(alterationUseCaseInput);

        AlterationUseCaseInput alterationUseCaseInputSecondTime = new AlterationUseCaseInput(expectedCpf, resultInitial.token(), null);
        var resultSecondTime = alterationUseCase.execute(alterationUseCaseInputSecondTime);

        assertEquals("BIOMETRIC", resultSecondTime.state());
        assertNotNull(resultSecondTime.token());
        assertEquals(expectedScore, resultSecondTime.metadata().get("score"));
        assertEquals(expectedResult, resultSecondTime.metadata().get("result"));
    }


}
