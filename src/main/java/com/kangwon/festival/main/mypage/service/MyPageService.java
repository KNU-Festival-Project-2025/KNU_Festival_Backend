package com.kangwon.festival.main.mypage.service;


import com.kangwon.festival.global.annotation.MethodDescription;
import com.kangwon.festival.global.entity.UserInfo;
import com.kangwon.festival.global.exception.ServiceException;
import com.kangwon.festival.main.mypage.dto.GuestbookResponse;
import com.kangwon.festival.main.mypage.dto.UserBlockResponse;
import com.kangwon.festival.main.mypage.repository.MypageGuestbookRepository;
import com.kangwon.festival.main.mypage.repository.MypageUserBlockRepository;
import com.kangwon.festival.main.mypage.repository.MypageUserInfoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

import static com.kangwon.festival.global.exception.Code.*;

@Service
@RequiredArgsConstructor
@Transactional
public class MyPageService {

    private final MypageUserInfoRepository userInfoRepository;
    private final MypageGuestbookRepository guestbookRepository;
    private final MypageUserBlockRepository userBlockRepository;

    @MethodDescription(description = "닉네임을 변경합니다.")
    public void updateNickname(Long userId, String newNickname){
        UserInfo user = userInfoRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다."));

        // 현재의 닉네임과 동일한지 확인
        if (user.getUserNickname().equals(newNickname)) {
            throw new ServiceException(SAME_NICKNAME);
        }

        // 다른 사용자가 사용 중인 닉네임 불가
        if (userInfoRepository.findByUserNickname(newNickname).isPresent()) {
            throw new ServiceException(DUPLICATE_NICKNAME);
        }

        user.changeNickname(newNickname);
    }

    @MethodDescription(description = "회원 탈퇴 처리합니다.")
    @Transactional
    public void deleteUser(Long userId) {
        UserInfo user = userInfoRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(CAN_NOT_FIND_USER));

        // 이미 탈퇴한 회원이면 예외
        if (user.isDeleted()) {
            throw new ServiceException(CAN_NOT_FIND_USER);
        }

        // 탈퇴 처리
        user.delete();
    }

    @MethodDescription(description = "내 방명록을 조회합니다.")
    public List<GuestbookResponse> getMyGuestbook(Long userId) {
        UserInfo user = userInfoRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(CAN_NOT_FIND_USER));

        return guestbookRepository.findByUser(user).stream()
                .map(GuestbookResponse::from)
                .toList();
    }

    @MethodDescription(description = "내가 차단한 사용자 목록을 조회합니다.")
    public List<UserBlockResponse> getBlockedUsers(Long userId) {
        UserInfo user = userInfoRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(CAN_NOT_FIND_USER));

        return userBlockRepository.findByBlocker(user).stream()
                .map(UserBlockResponse::from)
                .toList();
    }

    @MethodDescription(description = "특정 사용자의 차단을 해제합니다.")
    @Transactional
    public void unblockUser(Long blockerId, Long blockedId) {
        UserInfo blocker = userInfoRepository.findById(blockerId)
                .orElseThrow(() -> new ServiceException(CAN_NOT_FIND_BLOCKER_USER));
        UserInfo blocked = userInfoRepository.findById(blockedId)
                .orElseThrow(() -> new ServiceException(CAN_NOT_FIND_BLOCKED_USER));

        userBlockRepository.deleteByBlockerAndBlocked(blocker, blocked);
    }

}
