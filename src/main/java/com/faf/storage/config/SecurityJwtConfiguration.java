package com.faf.storage.config;

import com.faf.storage.security.SecurityMetersService;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.util.Base64;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import static com.faf.storage.util.SecurityUtils.AUTHORITIES_KEY;
import static com.faf.storage.util.SecurityUtils.JWT_ALGORITHM;

@Configuration
public class SecurityJwtConfiguration {

    @Bean
    public JwtDecoder jwtDecoder(SecurityMetersService metersService) {
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withSecretKey(getSecretKey()).macAlgorithm(JWT_ALGORITHM).build();
        return token -> {
            try {
                return jwtDecoder.decode(token);
            } catch (Exception e) {
                if (e.getMessage().contains("Invalid signature")) {
                    metersService.trackTokenInvalidSignature();
                } else if (e.getMessage().contains("Jwt expired at")) {
                    metersService.trackTokenExpired();
                } else if (e.getMessage().contains("Invalid JWT serialization")) {
                    metersService.trackTokenMalformed();
                } else if (e.getMessage().contains("Invalid unsecured/JWS/JWE")) {
                    metersService.trackTokenMalformed();
                }
                throw e;
            }
        };
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        return new NimbusJwtEncoder(new ImmutableSecret<>(getSecretKey()));
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthorityPrefix("");
        grantedAuthoritiesConverter.setAuthoritiesClaimName(AUTHORITIES_KEY);

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    private SecretKey getSecretKey() {
        String JWT_KEY = "MTkwZDgwOTU5OTRhZjYwYmZhMzQxYjdlZmExMDc4YTJjN2Q5MWVkY2ZmN2JmYzljZWQ1ZWNmN2ZlZDljYjdjMzU5ODZlODA4MzhhMGIxOTg5ZGNjZjZlMDA3NjZiMjMzMjI5MjJlZTFhNjQ1MTVmNmRjYjZkMWM5Yjg1MGM4ZmE=";
        byte[] keyBytes = Base64.from(JWT_KEY).decode();
        return new SecretKeySpec(keyBytes, 0, keyBytes.length, JWT_ALGORITHM.getName());
    }
}
