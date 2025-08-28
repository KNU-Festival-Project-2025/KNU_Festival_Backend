package com.kangwon.festival.domain.user.jwt;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.util.StringUtils.hasText;

import com.kangwon.festival.domain.user.exception.ExpiredTokenException;
import com.kangwon.festival.domain.user.exception.InValidTokenException;
import com.kangwon.festival.global.exception.InvalidInputException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String BEARER_HEADER = "Bearer ";
    private static final String BLANK = "";
    private final JwtTokenProvider jwtTokenProvider;


    /**
     * Same contract as for {@code doFilter}, but guaranteed to be just invoked once per request within a single request
     * thread. See {@link #shouldNotFilterAsyncDispatch()} for details.
     * <p>Provides HttpServletRequest and HttpServletResponse arguments instead of the
     * default ServletRequest and ServletResponse ones.
     *
     * @param request
     * @param response
     * @param filterChain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            val token = getAccessTokenFromRequest(request);
            if (hasText(token)) {
                // validateToken 내부에서 예외가 안 터지면 정상 토큰
                jwtTokenProvider.validateToken(token);

                val authentication = new UserAuthentication(getUserId(token), null, null);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception exception) {
            throw new InvalidInputException();
        }
        filterChain.doFilter(request, response);
    }

    private String getAccessTokenFromRequest(HttpServletRequest request) {
        return isContainsAccessToken(request) ? getAuthorizationAccessToken(request) : null;
    }

    private boolean isContainsAccessToken(HttpServletRequest request) {
        String authorization = request.getHeader(AUTHORIZATION);
        return authorization != null && authorization.startsWith(BEARER_HEADER);
    }

    private String getAuthorizationAccessToken(HttpServletRequest request) {
        return request.getHeader(AUTHORIZATION).replaceFirst(BEARER_HEADER, BLANK);
    }

    private long getUserId(String token) {
        return jwtTokenProvider.getUserFromJwt(token);
    }
}
