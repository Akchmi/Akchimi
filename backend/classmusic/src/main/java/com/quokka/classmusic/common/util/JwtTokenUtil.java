package com.quokka.classmusic.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.*;

import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * jwt 토큰 유틸 정의.
 */
@Component
@Slf4j
public class JwtTokenUtil {
    private String secretKey;
    private Integer accessExpirationTime;
    private Integer refreshExpirationTime;
    private String issuer;
    private JWTVerifier jwtVerifier;

    public final String TOKEN_PREFIX = "Bearer ";
    public final String HEADER_STRING = "Authorization";

    @Autowired
    public JwtTokenUtil(@Value("${jwt.issuer}") String issuer, @Value("${jwt.secret}") String secretKey, @Value("${jwt.access-expiration}") Integer accessExpirationTime, @Value("${jwt.refresh-expiration}") Integer refreshExpirationTime) {
        this.issuer = issuer;
        this.secretKey = secretKey;
        this.accessExpirationTime = accessExpirationTime;
        this.refreshExpirationTime = refreshExpirationTime;
        this.jwtVerifier = generateVerifier();
    }

    public String generateAccessJwt(int userId) {
        Date expires = getTokenExpiration(accessExpirationTime);

        String accessToken = JWT.create()
                .withExpiresAt(expires)
                .withIssuer(issuer)
                .withClaim("userId", userId)
                .sign(Algorithm.HMAC256(secretKey.getBytes()));

        log.debug("Access Token 생성 : {}", accessToken);

        return accessToken;
    }

    public String generateRefreshJwt(int userId) {
        Date expires = getTokenExpiration(refreshExpirationTime);

        String refreshToken = JWT.create()
                .withExpiresAt(expires)
                .withIssuer(issuer)
                .withClaim("userId", userId)
                .sign(Algorithm.HMAC256(secretKey.getBytes()));

        log.debug("Refresh Token 생성 : {}", refreshToken);

        return refreshToken;
    }

    public DecodedJWT validateJwtToken(String token) {
        try {
            DecodedJWT decodedJWT = this.jwtVerifier.verify(token);
            log.debug("JWT Token Decode...");
            log.debug("UserId : {}", decodedJWT.getClaim("userId"));

            return decodedJWT;
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("JWT 인증 오류");
        }
    }

    public void handleError(JWTVerifier verifier, String token) {
        try {
            verifier.verify(token.replace(TOKEN_PREFIX, ""));
        } catch (AlgorithmMismatchException ex) {
            throw ex;
        } catch (InvalidClaimException ex) {
            throw ex;
        } catch (SignatureGenerationException ex) {
            throw ex;
        } catch (SignatureVerificationException ex) {
            throw ex;
        } catch (TokenExpiredException ex) {
            throw ex;
        } catch (JWTCreationException ex) {
            throw ex;
        } catch (JWTDecodeException ex) {
            throw ex;
        } catch (JWTVerificationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        }
    }

    private JWTVerifier generateVerifier() {
        return JWT
                .require(Algorithm.HMAC256(secretKey.getBytes()))
                .withIssuer(issuer)
                .build();
    }

    private Date getTokenExpiration(int expirationTime) {
        Date now = new Date();
        return new Date(now.getTime() + expirationTime);
    }
}
