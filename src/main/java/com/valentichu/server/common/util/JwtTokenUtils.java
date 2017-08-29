package com.valentichu.server.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Jwt加密和解密的工具类
 *
 * @author Valentichu
 * created on 2017/08/25
 */
@Component
public class JwtTokenUtils {
    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "crt";

    @Value("${jwt.expiration}")
    private Long expiration;
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    public String getUserNameFromToken(String originalToken) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(originalToken);
            username = claims.getSubject();
            return username;
        } catch (Exception e) {
            return null;
        }
    }

    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userName);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    public String refreshToken(String oldOriginalToken) {
        if (isTokenExpired(oldOriginalToken)) {
            return null;
        }

        try {
            final Claims claims = getClaimsFromToken(oldOriginalToken);
            claims.put(CLAIM_KEY_CREATED, new Date());
            return generateToken(claims);
        } catch (Exception e) {
            return null;
        }
    }

    private Claims getClaimsFromToken(String originalToken) {
        try {
            final String token = originalToken.substring(tokenHead.length());
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    private Date getExpirationDateFromToken(String originalToken) {
        try {
            final Claims claims = getClaimsFromToken(originalToken);
            return claims.getExpiration();
        } catch (Exception e) {
            return null;
        }
    }

    private Boolean isTokenExpired(String originalToken) {
        try {
            final Date expiration = getExpirationDateFromToken(originalToken);
            return expiration.before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    private String generateToken(Map<String, Object> claims) {
        final String originalToken = Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
        return tokenHead + originalToken;
    }
}
