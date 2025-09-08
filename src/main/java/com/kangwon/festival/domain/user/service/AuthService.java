package com.kangwon.festival.domain.user.service;

import com.kangwon.festival.domain.user.dto.response.SignInResponse;
import com.kangwon.festival.domain.user.dto.Token;
import com.kangwon.festival.domain.user.entity.User;
import com.kangwon.festival.domain.user.exception.InValidTokenException;
import com.kangwon.festival.domain.user.exception.NotFoundUserException;
import com.kangwon.festival.domain.user.jwt.JwtTokenProvider;
import com.kangwon.festival.domain.user.jwt.UserAuthentication;
import com.kangwon.festival.domain.user.respository.UserRepository;
import com.kangwon.festival.global.annotation.MethodDescription;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    private static final int ACCESS_TOKEN_EXPIRATION = 7200000;
    private static final int REFRESH_TOKEN_EXPIRATION = 1209600000;

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final KakaoService kakaoService;

    @MethodDescription(description = "로그인을 진행합니다.")
    @Transactional
    public SignInResponse signIn(String kakaoAccessToken) {
        User user = getUser(kakaoAccessToken);
        Token token = getToken(user);
        return SignInResponse.of(token);
    }

    @MethodDescription(description = "로그아웃을 진행합니다.")
    @Transactional
    public void signOut(long userId) {
        User user = findUser(userId);
        user.resetRefreshToken();
    }

    @MethodDescription(description = "회원을 탈퇴합니다.")
    @Transactional
    public void withdraw(long userId) {
        User user = findUser(userId);
        deleteUser(user);
    }

    @MethodDescription(description = "리프레시 토큰으로 새로운 토큰을 재발급합니다.")
    @Transactional
    public SignInResponse reissue(String refreshToken) {
        jwtTokenProvider.validateToken(refreshToken);

        Long userId = jwtTokenProvider.getUserFromJwt(refreshToken);
        User user = findUser(userId);

        String saved = user.getRefreshToken();
        if (!Objects.equals(saved, refreshToken)) throw new InValidTokenException("유효하지 않은 리프레시 토큰입니다.");


        Token token = generatetoken(new UserAuthentication(user.getId(), null, null));
        user.updateRefreshToken(token.getRefreshToken());

        return SignInResponse.of(token);
    }

    @MethodDescription(description = "Kakao에서 유저 정보를 조회합니다.")
    private User getUser(String kakaoAccessToken) {
        String kakaoId = kakaoService.getKakaoData(kakaoAccessToken);
        return signUp(kakaoId);
    }

    @MethodDescription(description = "KakaoId를 조회합니다. 조회된 Id가 없는 경우 새롭게 가입을 진행합니다.")
    private User signUp(String kakaoId) {
        return userRepository.findByKakaoId(kakaoId).
                orElseGet(() -> saveUser(kakaoId));
    }

    @MethodDescription(description = "유저를 저장합니다.")
    private User saveUser(String kakaoId) {
        User user = User.builder()
                .kakaoId(kakaoId)
                .build();
        return userRepository.save(user);
    }

    @MethodDescription(description = "토큰을 발급받습니다.")
    private Token getToken(User user) {
        Token token = generatetoken(new UserAuthentication(user.getId(), null, null));
        user.updateRefreshToken(token.getRefreshToken());
        return token;
    }

    @MethodDescription(description = "토큰을 발급받습니다.")
    private Token generatetoken(Authentication authentication) {
        return Token.builder()
                .accessToken(jwtTokenProvider.generateToken(authentication, ACCESS_TOKEN_EXPIRATION))
                .refreshToken(jwtTokenProvider.generateToken(authentication, REFRESH_TOKEN_EXPIRATION))
                .build();
    }

    @MethodDescription(description = "유저를 조회합니다.")
    private User findUser(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(NotFoundUserException::new);
    }

    @MethodDescription(description = "유저를 삭제합니다.")
    private void deleteUser(User user) {
        userRepository.delete(user);
    }

}
