package com.swapnil.bookstore.app.util;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class JwtUtil {

    private static final String SECRET =
            "this-is-a-very-long-256-bit-secret-key-for-bookstore-app";

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    public JwtUtil() {
        SecretKey secretKey = new SecretKeySpec(
                SECRET.getBytes(),
                "HmacSHA256"
        );

        this.jwtEncoder = new NimbusJwtEncoder(
                new ImmutableSecret<>(secretKey)
        );

        this.jwtDecoder = NimbusJwtDecoder
                .withSecretKey(secretKey)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();
    }

    public String generate(String username) {
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(username)
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .build();

        return jwtEncoder.encode(
                JwtEncoderParameters.from(
                        JwsHeader.with(MacAlgorithm.HS256).build(),
                        claims
                )
        ).getTokenValue();
    }

    public String extractUsername(String token) {
        return jwtDecoder.decode(token).getSubject();
    }

    public boolean validateToken(String token) {
        try {
            jwtDecoder.decode(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
