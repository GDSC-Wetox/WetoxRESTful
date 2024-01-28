package dev.wetox.WetoxRESTful.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.wetox.WetoxRESTful.exception.OIDCInvalidHeaderException;
import dev.wetox.WetoxRESTful.exception.OIDCInvalidPublicKeyIdException;
import dev.wetox.WetoxRESTful.exception.OIDCValidationFailException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigInteger;
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
        PublicKey publicKey = getPublicKey(openId);
        String subject = null;
        try {
            subject = Jwts.parser()
                    .verifyWith(publicKey)
                    .build()
                    .parseSignedClaims(openId)
                    .getPayload()
                    .getSubject();
        } catch (JwtException e) {
            throw new OIDCValidationFailException();
        }
        return subject;
    }

    private PublicKey getPublicKey(String openId) {
        String kid = extractKeyId(openId);
        PublicKey publicKey = publicKeys.get(kid);
        if (publicKey == null) { // kid 공개키가 존재하지 않는다면 공개키 리스트를 업데이트
            generatePublicKeys();
            publicKey = publicKeys.get(kid); // kid 공개키 탐색 재시도
        }
        if(publicKey == null) { // kid 공개키가 이번에도 존재하지 않는다면 잘못된 kid라 판정
            throw new OIDCInvalidPublicKeyIdException();
        }
        return publicKey;
    }

    private void generatePublicKeys() {
        publicKeys = new HashMap<>();
        List<KakaoOIDCJwkResponse> jwks = requestJwks();
        jwks.forEach(jwk -> publicKeys.put(jwk.getKid(), generatePublicKey(jwk)));
    }

    private List<KakaoOIDCJwkResponse> requestJwks() {
        RestTemplate restTemplate = new RestTemplate();
        KakaoOIDCJwksResponse response = restTemplate.getForObject("https://kauth.kakao.com/.well-known/jwks.json", KakaoOIDCJwksResponse.class);
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

    private String extractKeyId(String openId) {
        ObjectMapper objectMapper = new ObjectMapper();
        String[] split = openId.split("\\.");
        String headerBase64Encoded = split[0];
        byte[] header = Base64.getDecoder().decode(headerBase64Encoded);
        KakaoOIDCHeaderResponse headerResponse = null;
        try {
            headerResponse = objectMapper.readValue(header, KakaoOIDCHeaderResponse.class);
        } catch (IOException e) {
            throw new OIDCInvalidHeaderException();
        }
        return headerResponse.getKid();
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
