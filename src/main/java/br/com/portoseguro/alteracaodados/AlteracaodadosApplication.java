package br.com.portoseguro.alteracaodados;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AlteracaodadosApplication implements CommandLineRunner {

	private final JwtConfig jwtConfig;

    public AlteracaodadosApplication(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public static void main(String[] args) {
		SpringApplication.run(AlteracaodadosApplication.class, args);
	}

	@Override
	public void run(String... args)  {
		StateToken.setSecretKey(jwtConfig.getSecretKey());
	}

}
