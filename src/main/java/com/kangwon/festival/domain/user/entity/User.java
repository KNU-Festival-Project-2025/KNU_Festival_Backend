package com.kangwon.festival.domain.user.entity;


import com.kangwon.festival.global.entity.BaseTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
public class User extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String kakaoId;
    private String refreshToken;
    private String profileImgUrl;
    private String phone;
    private String nickname;

    @Builder
    public User(String kakaoId, String profileImgUrl, String phone, String nickname) {
        this.kakaoId = kakaoId;
        this.profileImgUrl = profileImgUrl;
        this.phone = phone;
        this.nickname = nickname;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void resetRefreshToken() {
        this.refreshToken = null;
    }
}
