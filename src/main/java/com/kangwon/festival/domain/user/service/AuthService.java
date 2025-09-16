package com.kangwon.festival.domain.user.service;

import com.kangwon.festival.domain.security.dto.CustomUserDetails;
import com.kangwon.festival.domain.user.dto.KaKaoUserResponse;
import com.kangwon.festival.domain.user.dto.SignInRequest;
import com.kangwon.festival.domain.user.dto.SignInResponse;
import com.kangwon.festival.domain.security.dto.Token;
import com.kangwon.festival.domain.user.dto.ValidationGroups;
import com.kangwon.festival.domain.user.entity.User;
import com.kangwon.festival.domain.user.exception.DuplicateNicknameException;
import com.kangwon.festival.domain.user.exception.InValidTokenException;
import com.kangwon.festival.domain.user.exception.NotFoundUserException;
import com.kangwon.festival.domain.security.jwt.JwtTokenProvider;
import com.kangwon.festival.domain.security.jwt.UserAuthentication;
import com.kangwon.festival.domain.user.respository.UserRepository;
import com.kangwon.festival.global.annotation.MethodDescription;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
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
    private final KakaoOAuthClient kakaoOAuthClient;
    private final Validator validator;

    @MethodDescription(description = "로그인/회원가입(신규 시만 닉네임·전화번호 검증 및 저장)")
    @Transactional
    public SignInResponse signIn(SignInRequest request) {
        String kakaoAccessToken = kakaoOAuthClient.exchangeCodeForAccessToken(request.code());
        KaKaoUserResponse kakao = kakaoService.getKakaoData(kakaoAccessToken);
        boolean isNew = !userRepository.existsByKakaoId(kakao.id());

        if (isNew) validateForSignUp(request);

        User user = getUser(kakaoAccessToken, request);
        Token token = getToken(user);
        return SignInResponse.from(token);
    }

    @MethodDescription(description = "신규 가입 검증(그룹)")
    private void validateForSignUp(SignInRequest req) {
        Set<ConstraintViolation<SignInRequest>> v = validator.validate(req, ValidationGroups.SignUp.class);
        if (!v.isEmpty()) throw new ConstraintViolationException(v);
    }

    @MethodDescription(description = "로그아웃을 진행합니다.")
    @Transactional
    public void signOut(CustomUserDetails customUserDetails) {
        User user = findUser(customUserDetails);
        user.resetRefreshToken();
    }

    @MethodDescription(description = "회원을 탈퇴합니다.")
    @Transactional
    public void withdraw(CustomUserDetails customUserDetails) {
        User user = findUser(customUserDetails);
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


        Token token = generateToken(new UserAuthentication(user.getId(), null, null));
        user.updateRefreshToken(token.refreshToken());

        return SignInResponse.from(token);
    }

    @MethodDescription(description = "Kakao에서 유저 정보를 조회합니다.")
    private User getUser(String kakaoAccessToken, SignInRequest request) {
        KaKaoUserResponse kakao = kakaoService.getKakaoData(kakaoAccessToken);
        return signUp(kakao, request);
    }

    @MethodDescription(description = "KakaoId를 조회합니다. 조회된 Id가 없는 경우 새롭게 가입을 진행합니다.")
    private User signUp(KaKaoUserResponse kakao, SignInRequest request) {
        return userRepository.findByKakaoId(kakao.id())
                .orElseGet(() -> {
                    if (userRepository.existsByNickname(request.nickname())) {
                        throw new DuplicateNicknameException();
                    }
                    return saveUser(kakao, request);
                });
    }

    @MethodDescription(description = "유저를 저장합니다.")
    private User saveUser(KaKaoUserResponse kakao, SignInRequest request) {
        User user = User.builder()
                .kakaoId(kakao.id())
                .nickname(request.nickname())
                .phone(request.phone())
                .profileImgUrl(kakao.profileImgUrl())
                .build();
        return userRepository.save(user);
    }

    @MethodDescription(description = "토큰을 발급받습니다.")
    private Token getToken(User user) {
        Token token = generateToken(new UserAuthentication(user.getId(), null, null));
        user.updateRefreshToken(token.refreshToken());
        return token;
    }

    @MethodDescription(description = "토큰을 발급받습니다.")
    private Token generateToken(Authentication authentication) {
        return Token.builder()
                .accessToken(jwtTokenProvider.generateToken(authentication, ACCESS_TOKEN_EXPIRATION))
                .refreshToken(jwtTokenProvider.generateToken(authentication, REFRESH_TOKEN_EXPIRATION))
                .build();
    }

    @MethodDescription(description = "유저를 조회합니다. (SecurityContext 기반)")
    private User findUser(CustomUserDetails principal) {
        return Optional.ofNullable(principal)
                .map(CustomUserDetails::getUser)
                .orElseThrow(NotFoundUserException::new);
    }

    @MethodDescription(description = "유저를 조회합니다. (PK 기반)")
    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(NotFoundUserException::new);
    }

    @MethodDescription(description = "유저를 삭제합니다.")
    private void deleteUser(User user) {
        userRepository.delete(user);
    }

}
