package com.kangwon.festival.domain.user.jwt;

import static io.jsonwebtoken.security.Keys.hmacShaKeyFor;
import static java.util.Base64.getEncoder;

import com.kangwon.festival.domain.user.exception.ExpiredTokenException;
import com.kangwon.festival.domain.user.exception.InValidTokenException;
import com.kangwon.festival.global.config.ValueConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final ValueConfig valueConfig;

    public String generateToken(Authentication authentication, long expiration) {
        return Jwts.builder()
                .setClaims(generateClaims(authentication))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }

    private Claims generateClaims(Authentication authentication) {
        Claims claims = Jwts.claims();
        claims.put("userId", authentication.getPrincipal());
        return claims;
    }

    private SecretKey getSigningKey() {
        String encodedKey = getEncoder().encodeToString(valueConfig.getSecretKey().getBytes());
        return hmacShaKeyFor(encodedKey.getBytes());
    }

    public void validateToken(String token) {
        try {
            getBody(token); // JWT 파싱
        } catch (MalformedJwtException exception) {
            log.error(exception.getMessage(), exception);
            throw new InValidTokenException("잘못된 JWT 토큰입니다.");
        } catch (ExpiredJwtException exception) {
            log.error(exception.getMessage(), exception);
            throw new ExpiredTokenException("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException exception) {
            log.error(exception.getMessage(), exception);
            throw new InValidTokenException("지원하지 않는 JWT 토큰 형식입니다.");
        } catch (IllegalArgumentException exception) {
            log.error(exception.getMessage(), exception);
            throw new InValidTokenException("JWT 토큰이 비어있습니다.");
        }
    }

    private Claims getBody(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Long getUserFromJwt(String token) {
        Claims claims = getBody(token);
        return Long.parseLong(claims.get("userId").toString());
    }
}
