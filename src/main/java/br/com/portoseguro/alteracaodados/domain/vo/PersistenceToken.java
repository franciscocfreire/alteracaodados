package br.com.portoseguro.alteracaodados.domain.vo;

import br.com.portoseguro.alteracaodados.domain.exceptions.ValidationError;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.Getter;

import javax.crypto.SecretKey;
import java.util.Date;

@Getter
public class PersistenceToken {
    private static SecretKey SECRET_KEY;
    private final Claims claims;

    private final String subject = "state-token";
    private final String state;
    private final String nextState;
    private final Date issuedAt;

    public static void setSecretKey(SecretKey secretKey) {
        SECRET_KEY = secretKey;
    }

    private PersistenceToken(String state, String nextState, Date date) {
        this.issuedAt = date;
        this.state = state;
        this.nextState = nextState;
        Claims claims = new DefaultClaims();
        claims.put("state", state);
        claims.put("nextState", nextState);
        this.claims = claims;
        if (!PersistenceToken.isValid(generateToken())) throw new ValidationError("Token is not valid", -1);
    }

    public String getValue() {
        return generateToken();
    }

    public static boolean isValid(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getCurrentState() {
        return state;
    }

    public static PersistenceToken restore(String state, String nextState, Date date) {
        return new PersistenceToken(state, nextState, date);
    }

    public static PersistenceToken restoreByToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
        String state = claims.get("state", String.class);
        String nextState = claims.get("nextState", String.class);
        return new PersistenceToken(state, nextState, claims.getIssuedAt());
    }

    private String generateToken() {
        return Jwts.builder()
                .setSubject(this.subject)
                .addClaims(this.claims)
                .setIssuedAt(this.issuedAt)
                .signWith(SECRET_KEY)
                .compact();
    }


}
