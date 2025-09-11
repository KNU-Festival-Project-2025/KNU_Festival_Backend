package com.kangwon.festival.domain.user.respository;

import com.kangwon.festival.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByKakaoId(String kakaoId);
    boolean existsByKakaoId(String kakaoId);
    boolean existsByNickname(String nickname);
}
