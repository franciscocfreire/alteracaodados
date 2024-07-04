package br.com.portoseguro.alteracaodados.domain.entity;

import lombok.Getter;

@Getter
public class User {

    private final String id;
    private final String cpf;
    private final String maskedEmail;
    private final String maskedPhone;

    public User(String id, String cpf, String maskedEmail, String maskedPhone) {
        this.id = id;
        this.cpf = cpf;
        this.maskedEmail = maskedEmail;
        this.maskedPhone = maskedPhone;
    }

}