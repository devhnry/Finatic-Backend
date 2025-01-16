package dev.finaticbackend.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
@Slf4j
public class JwtService {
    private static final long Expiration_Time = 86_400; // 24hrs in Seconds
    private SecretKey secretKey;

    public JwtService() {
        String secretString = "HASDGHFJKODJGNKCJOQ&IRU123HKNCS890SDCSLNLMS7654MLJJHNAMFLJNONAXM&KNCKXCDJA";
        byte[] secretBytes = Base64.getDecoder().decode(secretString.getBytes(StandardCharsets.UTF_8));
        this.secretKey = new SecretKeySpec(secretBytes, "HmacSHA256");
    }

}
