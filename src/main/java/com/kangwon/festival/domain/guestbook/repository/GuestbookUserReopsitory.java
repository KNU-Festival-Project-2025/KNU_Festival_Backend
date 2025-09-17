package com.kangwon.festival.domain.guestbook.repository;

import com.kangwon.festival.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GuestbookUserReopsitory extends JpaRepository<User, Long> {
}
