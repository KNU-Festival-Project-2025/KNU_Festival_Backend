package com.kangwon.festival.domain.admin.repository;

import com.kangwon.festival.global.entity.GuestbookInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminGuestbookInfoRepository extends JpaRepository<GuestbookInfo, Integer> {

}