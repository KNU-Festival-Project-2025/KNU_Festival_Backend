package com.kangwon.festival.main.mypage.service;

import com.kangwon.festival.global.exception.Code;
import com.kangwon.festival.global.exception.InvalidInputException;
import com.kangwon.festival.global.exception.ServiceException;
import com.kangwon.festival.main.mypage.domain.*;
import com.kangwon.festival.main.mypage.repository.GuestbookRepository;
import com.kangwon.festival.main.mypage.repository.UserBlockRepository;
import com.kangwon.festival.main.mypage.repository.UserInfoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final UserInfoRepository userInfoRepository;
    private final GuestbookRepository guestbookRepository;
    private final UserBlockRepository userBlockRepository;

    public void updateNickname(int userId, String newNickname){
        UserInfo user = userInfoRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다."));

        // 현재의 닉네임과 동일한지 확인
        if (user.getUserNickname().equals(newNickname)) {
            throw new ServiceException(Code.SAME_NICKNAME);
        }

        // 다른 사용자가 사용 중인 닉네임 불가
        if (userInfoRepository.findByUserNickname(newNickname).isPresent()) {
            throw new ServiceException(Code.DUPLICATE_NICKNAME);
        }

        user.setUserNickname(newNickname);
        user.setUpdatedAt(LocalDateTime.now());
        userInfoRepository.save(user);
    }

    // 회원 탈퇴
    @Transactional
    public void deleteUser(int userId) {
        UserInfo user = userInfoRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(Code.CAN_NOT_FIND_USER));

        // 이미 탈퇴한 회원이면 예외
        if (user.isDeleted()) {
            throw new ServiceException(Code.CAN_NOT_FIND_USER);
        }

        // 탈퇴 처리
        user.setDeleted(true); // isDeleted = true
        user.setUpdatedAt(LocalDateTime.now());

        userInfoRepository.save(user);
    }

    // 내 방명록 조회
    public List<GuestbookResponse> getMyGuestbook(int userId) {
        UserInfo user = userInfoRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(Code.CAN_NOT_FIND_USER));

        return guestbookRepository.findByUser(user).stream()
                .map(g -> new GuestbookResponse(
                        g.getGuestbookId(),
                        g.getUser().getUserNickname(),
                        g.getUser().getUserId(),
                        g.isGuestbookIsAnonymous(),
                        g.getGuestbookTitle(),
                        g.getGuestbookContent(),
                        g.getCreatedAt(),
                        g.getUpdatedAt()
                ))
                .toList();
    }

    // 차단 목록 조회
    public List<UserBlockResponse> getBlockedUsers(int userId) {
        UserInfo user = userInfoRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(Code.CAN_NOT_FIND_USER));

        return userBlockRepository.findByBlocker(user).stream()
                .map(b -> new UserBlockResponse(
                        b.getUserBlockId(),
                        b.getBlocker().getUserId(),
                        b.getBlocker().getUserNickname(),
                        b.getBlocked().getUserId(),
                        b.getBlocked().getUserNickname(),
                        b.getCreatedAt()
                ))
                .toList();
    }

    // 차단 해제
    @Transactional
    public void unblockUser(int blockerId, int blockedId) {
        UserInfo blocker = userInfoRepository.findById(blockerId)
                .orElseThrow(() -> new ServiceException(Code.CAN_NOT_FIND_BLOCKER_USER));
        UserInfo blocked = userInfoRepository.findById(blockedId)
                .orElseThrow(() -> new ServiceException(Code.CAN_NOT_FIND_BLOCKED_USER));

        userBlockRepository.deleteByBlockerAndBlocked(blocker, blocked);
    }

}
