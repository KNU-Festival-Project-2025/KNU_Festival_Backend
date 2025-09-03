package com.kangwon.festival.main.guestbook.repository;

import com.kangwon.festival.global.entity.GuestbookHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestbookHistoryRepository extends JpaRepository<GuestbookHistory, Long> {

}
