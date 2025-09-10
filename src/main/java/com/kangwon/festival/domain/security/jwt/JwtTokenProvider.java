package com.kangwon.festival.domain.security.jwt;

import com.kangwon.festival.domain.user.entity.Role;
import com.kangwon.festival.domain.user.exception.ExpiredTokenException;
import com.kangwon.festival.domain.user.exception.InValidTokenException;
import com.kangwon.festival.global.annotation.MethodDescription;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String secretKey;

    @MethodDescription(description = "인증 정보를 기반으로 JWT 토큰을 생성합니다.")
    public String generateToken(Authentication authentication, long expiration) {
        return Jwts.builder()
                .setClaims(generateClaims(authentication))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @MethodDescription(description = "Authentication 객체로부터 Claims를 생성합니다.")
    private Claims generateClaims(Authentication authentication) {
        Claims claims = Jwts.claims();
        claims.put("userId", authentication.getPrincipal());

        String role = authentication.getAuthorities()
                .stream()
                .findFirst()
                .map(auth -> auth.getAuthority())
                .orElse(Role.USER.name());

        claims.put("role", Role.valueOf(role).name());
        return claims;
    }

    @MethodDescription(description = "JWT 서명에 사용할 SecretKey를 반환합니다.")
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @MethodDescription(description = "JWT 토큰의 유효성을 검증합니다.")
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

    @MethodDescription(description = "JWT 토큰에서 Claims(본문)를 추출합니다.")
    private Claims getBody(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @MethodDescription(description = "JWT 토큰에서 userId 클레임을 추출합니다.")
    public Long getUserFromJwt(String token) {
        Claims claims = getBody(token);
        return Long.parseLong(claims.get("userId").toString());
    }

    @MethodDescription(description = "JWT 토큰에서 role 클레임을 추출합니다.")
    public Role getRoleFromJwt(String token) {
        Claims claims = getBody(token);
        String roleStr = claims.get("role", String.class);
        return Role.valueOf(roleStr);
    }
}
