package dev.wetox.WetoxRESTful.auth;

import dev.wetox.WetoxRESTful.exception.OIDCInvalidPublicKeyIdException;
import dev.wetox.WetoxRESTful.exception.OIDCValidationFailException;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
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
        List<KakaoOIDCJwkResponse> jwks = requestJwks();
        jwks.forEach(jwk -> publicKeys.put(jwk.getKid(), generatePublicKey(jwk)));
    }

    private List<KakaoOIDCJwkResponse> requestJwks() {
        RestTemplate restTemplate = new RestTemplate();
        KakaoOIDCJwksResponse response = restTemplate
                .getForObject("https://kauth.kakao.com/.well-known/jwks.json", KakaoOIDCJwksResponse.class);
        return response.getKeys();
    }

    private PublicKey generatePublicKey(KakaoOIDCJwkResponse jwk) {
        try {
            BigInteger modulus = decodeBase64urlUint(jwk.getN());
            BigInteger exponent = decodeBase64urlUint(jwk.getE());
            RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(modulus, exponent);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(rsaPublicKeySpec);
        } catch (Exception e) {
            throw new OIDCInvalidPublicKeyIdException();
        }
    }

    private BigInteger decodeBase64urlUint(String base64urlUintEncoded) {
        String padded = base64urlUintEncoded.length() % 4 == 0 ?
                base64urlUintEncoded :
                base64urlUintEncoded + "====".substring(base64urlUintEncoded.length() % 4);
        String base64Encoded = padded.replace('_', '/').replace('-', '+');
        byte[] decoded = Base64.getDecoder().decode(base64Encoded);
        return new BigInteger(1, decoded);
    }
}
