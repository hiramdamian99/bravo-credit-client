package com.bravo.credit.client.application.sync.securityConfig;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final JwtEncoder jwtEncoder;

    public AuthController(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    @PostMapping("/token")
    public ResponseEntity<Map<String, String>> token(@RequestBody Map<String, String> body) {

        String username = body.get("username");
        String password = body.get("password");

        if (!"supervisor".equals(username) || !"1234".equals(password)) {
            return ResponseEntity.status(401).body(Map.of("error", "invalid credentials"));
        }

        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("bravo-credit-client")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(60 * 60)) // 1 hora
                .subject(username)
                .claim("roles", List.of("SUPERVISOR"))
                .build();

        JwsHeader jwsHeader = JwsHeader.with(MacAlgorithm.HS256).build();

        String jwt = jwtEncoder.encode(
                JwtEncoderParameters.from(jwsHeader, claims)
        ).getTokenValue();

        return ResponseEntity.ok(Map.of("access_token", jwt));
    }
}
