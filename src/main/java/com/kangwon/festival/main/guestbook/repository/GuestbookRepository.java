package com.kangwon.festival.main.guestbook.repository;

import com.kangwon.festival.global.entity.GuestbookInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestbookRepository extends JpaRepository<GuestbookInfo, Long> {

}
