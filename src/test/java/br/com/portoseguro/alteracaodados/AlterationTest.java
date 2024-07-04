package br.com.portoseguro.alteracaodados;

import br.com.portoseguro.alteracaodados.domain.entity.User;
import br.com.portoseguro.alteracaodados.application.AlterationUseCase;
import br.com.portoseguro.alteracaodados.domain.vo.PersistenceToken;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AlterationTest {

    private static final String SECRET_KEY = "MjZmNDJlYjQtMDYzZS00ZjU4LWE0NmQtOGJlMWIwMjExZDM4Cg==";

    private AlterationUseCase alterationUseCase;
    @Mock
    private UserGateway userGateway;
    private User userMock;

    @BeforeEach
    void setUp() {
        SecretKey key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(SECRET_KEY));
        PersistenceToken.setSecretKey(key);

        userMock = new User("user123", "33998291830", "maskemail@gmail.com", "maskedphone");
        alterationUseCase = new AlterationUseCase(userGateway);
    }

    @Test
    @DisplayName("Deve testar o estado incial da alteração de dados")
    public void deveTestarEstadoInicialAlteracaoDados() {
        String expectedCpf = "33998291830";
        String expectedEmail = "maskemail@gmail.com";
        String expectedPhone = "maskedphone";
        String expectedNextState = "facialBiometric";
        AlterationUseCase.AlterationUseCaseInput alterationUseCaseInput = new AlterationUseCase.AlterationUseCaseInput(expectedCpf, null, null);
        when(userGateway.restoreByCpf(eq(expectedCpf))).thenReturn(userMock);
        var result = alterationUseCase.execute(alterationUseCaseInput);
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
        AlterationUseCase.AlterationUseCaseInput alterationUseCaseInput = new AlterationUseCase.AlterationUseCaseInput(expectedCpf, null, null);
        when(userGateway.restoreByCpf(eq(expectedCpf))).thenReturn(userMock);
        var resultInitial = alterationUseCase.execute(alterationUseCaseInput);

        AlterationUseCase.AlterationUseCaseInput alterationUseCaseInputSecondTime = new AlterationUseCase.AlterationUseCaseInput(expectedCpf, resultInitial.token(), null);
        var resultSecondTime = alterationUseCase.execute(alterationUseCaseInputSecondTime);

        assertEquals("facialBiometric", resultSecondTime.state());
        assertNotNull(resultSecondTime.token());
        assertEquals(expectedScore, resultSecondTime.metadata().get("score"));
        assertEquals(expectedResult, resultSecondTime.metadata().get("result"));
    }

    @Test
    @DisplayName("Deve retornar token nulo no ultimo estagio")
    public void deveRetornarTokenNuloUltimoEstagio() {
        String expectedCpf = "33998291830";
        AlterationUseCase.AlterationUseCaseInput alterationUseCaseInput = new AlterationUseCase.AlterationUseCaseInput(expectedCpf, null, null);
        when(userGateway.restoreByCpf(eq(expectedCpf))).thenReturn(userMock);
        var resultInitial = alterationUseCase.execute(alterationUseCaseInput);

        AlterationUseCase.AlterationUseCaseInput alterationUseCaseInputSecondTime = new AlterationUseCase.AlterationUseCaseInput(expectedCpf, resultInitial.token(), null);
        var resultSecondTime = alterationUseCase.execute(alterationUseCaseInputSecondTime);

        AlterationUseCase.AlterationUseCaseInput alterationUseCaseInputThirdTime = new AlterationUseCase.AlterationUseCaseInput(expectedCpf, resultSecondTime.token(), null);
        var resultThirdTime = alterationUseCase.execute(alterationUseCaseInputThirdTime);

        AlterationUseCase.AlterationUseCaseInput alterationUseCaseInputFourthTime = new AlterationUseCase.AlterationUseCaseInput(expectedCpf, resultThirdTime.token(), null);
        var resultFourthTime = alterationUseCase.execute(alterationUseCaseInputFourthTime);

        assertEquals("changeData", resultFourthTime.state());
        assertNull(resultFourthTime.token());
    }


}
