package com.kangwon.festival.domain.security.jwt;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.util.StringUtils.hasText;

import com.kangwon.festival.domain.security.dto.CustomUserDetails;
import com.kangwon.festival.domain.user.entity.Role;
import com.kangwon.festival.domain.user.entity.User;
import com.kangwon.festival.domain.user.exception.CustomJwtAuthenticationEntryPoint;
import com.kangwon.festival.domain.user.exception.NotFoundUserException;
import com.kangwon.festival.domain.user.respository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
    private final CustomJwtAuthenticationEntryPoint authenticationEntryPoint;
    private final UserRepository userRepository;


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

                long userId = getUserId(token);
                Role role = getRole(token);

                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new NotFoundUserException());

                CustomUserDetails principal = new CustomUserDetails(user);
                List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role.name()));

                var authentication = new UsernamePasswordAuthenticationToken(principal, null, authorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        } catch (AuthenticationException e) {
            SecurityContextHolder.clearContext();
            authenticationEntryPoint.commence(request, response, e);
            return;

        } catch (io.jsonwebtoken.JwtException e) {
            SecurityContextHolder.clearContext();
            authenticationEntryPoint.commence(request, response, new AuthenticationException("유효하지 않은 토큰입니다.", e) {});
            return;

        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            authenticationEntryPoint.commence(request, response, new AuthenticationException("인증 처리 중 오류가 발생했습니다.", e) {});
            return;
        }
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
    private Role getRole(String token) { return jwtTokenProvider.getRoleFromJwt(token);}
}
