package com.kangwon.festival.domain.photo.entity;

import com.kangwon.festival.domain.user.entity.User;
import com.kangwon.festival.global.entity.BaseTime;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "photo_like",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_photo_user", columnNames = {"photo_id", "user_id"})
        },
        indexes = {
                @Index(name = "idx_photo_like_photo", columnList = "photo_id"),
                @Index(name = "idx_photo_like_user", columnList = "user_id")
        })
public class PhotoLike extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "photo_id", nullable = false)
    private Photo photo;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    private PhotoLike(Photo photo, User user) {
        this.photo = photo;
        this.user = user;
    }
}
