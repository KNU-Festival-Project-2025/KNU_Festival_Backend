package com.kangwon.festival.domain.photo.entity;

import com.kangwon.festival.domain.user.entity.User;
import com.kangwon.festival.global.entity.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "photo")
public class Photo extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nickname;
    @Column(columnDefinition = "TEXT")
    private String imgUrl;
    @Column(columnDefinition = "TEXT")
    private String originImgUrl;
    @Column(columnDefinition = "TEXT")
    private String content;
    private long likeCount = 0L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    public Photo(User user, String nickname, String imgUrl, String originImgUrl, String content) {
        this.user = user;
        this.nickname = nickname;
        this.imgUrl = imgUrl;
        this.originImgUrl = originImgUrl;
        this.content = content;
    }
}
