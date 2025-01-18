package dev.finaticbackend.services;

import dev.finaticbackend.entities.BaseUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
@Slf4j
public class JwtService {
    private static final long Expiration_Time = 86_400; // 24hrs in Seconds
    private final SecretKey secretKey;

    public JwtService() {
        String secretString = "HASDGHFJKODJGNKCJOQ&IRU123HKNCS890SDCSLNLMS7654MLJJHNAMFLJNONAXM&KNCKXCDJA";
        byte[] secretBytes = Base64.getDecoder().decode(secretString.getBytes(StandardCharsets.UTF_8));
        this.secretKey = new SecretKeySpec(secretBytes, "HmacSHA256");
    }

    public String createToken(BaseUser user) {
        return generateToken(user);
    }

    public String createRefreshToken(BaseUser user, HashMap<String, Object> claims) {
        return generateRefreshToken(user, claims);
    }

    private String generateToken(BaseUser user) {
        HashMap<String, Object> claims = new HashMap<>();

        claims.put("userId", user.getUserId());
        claims.put("firstName", user.getFirstName());
        claims.put("lastName", user.getLastName());
        claims.put("email", user.getEmailAddress());
        claims.put("phoneNumber", user.getPhoneNumber());

        return Jwts.builder()
                .claims(claims)
                .subject(user.getEmailAddress())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + Expiration_Time))
                .signWith(secretKey)
                .compact();
    }

    private String generateRefreshToken(BaseUser user, HashMap<String, Object> claims) {
        return Jwts.builder()
                .claims(claims)
                .subject(user.getEmailAddress())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + Expiration_Time))
                .signWith(secretKey)
                .compact();
    }

    public <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
        return claimsTFunction.apply(Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload());
    }

    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }
}
