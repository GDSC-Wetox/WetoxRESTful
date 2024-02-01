package dev.wetox.WetoxRESTful.auth;

import dev.wetox.WetoxRESTful.exception.OIDCInvalidPublicKeyIdException;
import dev.wetox.WetoxRESTful.exception.OIDCValidationFailException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.JwkSet;
import io.jsonwebtoken.security.Jwks;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.Key;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

@Service
public class KakaoOIDCService {
    private Map<String, PublicKey> publicKeys = new HashMap<>();

    public String extractSubject(String openId) {
        try {
            return Jwts.parser()
                    .keyLocator(new LocatorAdapter<Key>() {
                        @Override
                        protected Key locate(ProtectedHeader header) {
                            String keyId = header.getKeyId();
                            PublicKey publicKey = publicKeys.get(keyId);
                            if (publicKey == null) {
                                generatePublicKeys();
                                publicKey = publicKeys.get(keyId);
                                if(publicKey == null) {
                                    throw new OIDCInvalidPublicKeyIdException();
                                }
                            }
                            return publicKey;
                        }
                    })
                    .requireIssuer("https://kauth.kakao.com")
                    .requireAudience("6cd6251287f6ca88346f4d50466956a2")
                    .build()
                    .parseSignedClaims(openId)
                    .getPayload()
                    .getSubject();
        } catch (JwtException e) {
            throw new OIDCValidationFailException();
        }
    }

    private void generatePublicKeys() {
        publicKeys = new HashMap<>();
        RestTemplate restTemplate = new RestTemplate();
        String jwksResponse = restTemplate.getForObject("https://kauth.kakao.com/.well-known/jwks.json", String.class);
        JwkSet jwks = Jwks.setParser()
                .build()
                .parse(jwksResponse);
        jwks.forEach(jwk -> publicKeys.put(jwk.getId(), (PublicKey)jwk.toKey()));
    }
}
