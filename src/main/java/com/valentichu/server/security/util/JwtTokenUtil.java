package com.valentichu.server.security.util;

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
 */
@Component
public class JwtTokenUtil {

    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "crt";

    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    private Claims getClaimsFromToken(String originalToken) {
        Claims claims;
        final String token = originalToken.substring(tokenHead.length());
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    public String getUsernameFromToken(String originalToken) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(originalToken);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    private Date getExpirationDateFromToken(String originalToken) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(originalToken);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    public Boolean isTokenExpired(String originalToken) {
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

    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userName);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    public String refreshToken(String oldOriginalToken) {
        String refreshedToken;
        if (!isTokenExpired(oldOriginalToken)) {
            try {
                final Claims claims = getClaimsFromToken(oldOriginalToken);
                claims.put(CLAIM_KEY_CREATED, new Date());
                refreshedToken = generateToken(claims);
            } catch (Exception e) {
                refreshedToken = null;
            }
            return refreshedToken;
        } else {
            return null;
        }
    }
}
