package com.kangwon.festival.domain.user.jwt;

import com.kangwon.festival.global.annotation.MethodDescription;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secretBase64;

    private SecretKey key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretBase64));
    }

    @MethodDescription(description = "토큰에서 userId를 추출합니다.")
    public String getUserId(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("userId", String.class);
    }
}
