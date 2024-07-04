package br.com.portoseguro.alteracaodados.domain.vo;

import br.com.portoseguro.alteracaodados.domain.exceptions.ValidationError;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.Getter;

import javax.crypto.SecretKey;
import java.util.Date;

@Getter
public class StateToken {
    private final String value;

    private static SecretKey SECRET_KEY;

    public static void setSecretKey(SecretKey secretKey) {
        SECRET_KEY = secretKey;
    }

    private StateToken(String value) {
        this.value = value;
        if (!this.isValid()) throw new ValidationError("Token is not valid", -1);
    }


    public String getValue() {
        if (getCurrentState().equals("done")) return null;
        return this.value;
    }

    private boolean isValid() {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getCurrentState() {
        Claims claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(value).getBody();
        return claims.get("state", String.class);
    }

    public static StateToken create() {
        return new StateToken(generateToken("initial"));
    }

    public static StateToken restoreByState(String state) {
        return new StateToken(generateToken(state));
    }

    public static StateToken restoreByToken(String token) {
        return new StateToken(token);
    }

    private static String generateToken(String state) {
        return Jwts.builder()
                .setSubject("state-token")
                .claim("state", state)
                .setIssuedAt(new Date())
                .signWith(SECRET_KEY)
                .compact();
    }


}
