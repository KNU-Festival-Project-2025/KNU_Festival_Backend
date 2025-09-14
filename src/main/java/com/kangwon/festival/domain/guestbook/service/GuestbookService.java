package com.kangwon.festival.domain.guestbook.service;

import com.kangwon.festival.domain.guestbook.repository.GuestbookUserReopsitory;
import com.kangwon.festival.domain.user.entity.User;
import com.kangwon.festival.global.annotation.MethodDescription;
import com.kangwon.festival.global.entity.GuestbookHistory;
import com.kangwon.festival.global.entity.GuestbookInfo;
import com.kangwon.festival.global.entity.UserInfo;
import com.kangwon.festival.global.exception.ServiceException;
import com.kangwon.festival.domain.guestbook.dto.GuestbookRequest;
import com.kangwon.festival.domain.guestbook.dto.GuestbookResponse;
import com.kangwon.festival.domain.guestbook.repository.GuestbookHistoryRepository;
import com.kangwon.festival.domain.guestbook.repository.GuestbookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.kangwon.festival.global.exception.Code.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GuestbookService {
    private final GuestbookRepository guestbookRepository;
    private final GuestbookHistoryRepository guestbookHistoryRepository;
    private final GuestbookUserReopsitory userRepository;

    @Transactional
    @MethodDescription(description = "방명록을 등록합니다.")
    public GuestbookResponse create(Integer userId, GuestbookRequest req) {
        // User.id가 Integer
        User writer = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(CAN_NOT_FIND_USER, "방명록- 해당 유저를 찾지 못하였습니다."));

        GuestbookInfo saved = guestbookRepository.save(
                GuestbookInfo.create(writer, req.getContent())
        );

        // 히스토리 저장
        guestbookHistoryRepository.save(
                GuestbookHistory.of(saved, writer, saved.getGuestbookContent())
        );

        return GuestbookResponse.from(saved);
    }

    @MethodDescription(description = "전체 방명록 목록을 조회합니다.")
    public List<GuestbookResponse> getAllGuestbooks() {
        return guestbookRepository.findByGuestbookIsDeletedFalseOrderByCreatedDateTimeDesc()
                .stream()
                .map(GuestbookResponse::from)
                .toList();
    }

    @Transactional
    @MethodDescription(description = "내가 쓴 방명록을 삭제합니다.")
    public void deleteMyGuestbook(Integer userId, Long guestbookId) {
        GuestbookInfo g = guestbookRepository.findByGuestbookIdAndGuestbookIsDeletedFalse(guestbookId)
                .orElseThrow(() -> new ServiceException(CAN_NOT_FIND_RESOURCE));

        if (!g.getUser().getId().equals(userId)) {
            throw new ServiceException(ACCESS_DENIED, "본인이 작성한 방명록만 삭제할 수 있습니다.");
        }

        g.deleted();
    }

    /*
    @MethodDescription(description = "특정 방명록을 조회합니다.")
    public GuestbookResponse getById(Long guestbookId) {
        GuestbookInfo g = guestbookRepository.findByGuestbookIdAndGuestbookIsDeletedFalse(guestbookId)
                .orElseThrow(() -> new ServiceException(CAN_NOT_FIND_RESOURCE));
        return GuestbookResponse.from(g);
    }

    @MethodDescription(description = "내 방명록 목록을 조회합니다.")
    public List<GuestbookResponse> getMyGuestbooks(Long userId) {
        UserInfo me = userInfoRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(CAN_NOT_FIND_USER));

        return guestbookRepository.findByUserAndGuestbookIsDeletedFalseOrderByCreatedDateTimeDesc(me)
                .stream()
                .map(GuestbookResponse::from)
                .toList();
    }


    @Transactional
    @MethodDescription(description = "내가 쓴 방명록을 수정합니다.")
    public GuestbookResponse updateMyGuestbook(Long userId, Long guestbookId, GuestbookRequest req) {
        GuestbookInfo g = guestbookRepository.findByGuestbookIdAndGuestbookIsDeletedFalse(guestbookId)
                .orElseThrow(() -> new ServiceException(CAN_NOT_FIND_RESOURCE));

        // 권한 체크
        if (!g.getUser().getUserId().equals(userId)) {
            throw new ServiceException(ACCESS_DENIED, "본인이 작성한 방명록만 수정할 수 있습니다.");
        }

        boolean changed = !safeEquals(g.getGuestbookContent(), req.getContent());
        if (!changed) {
            return GuestbookResponse.from(g);
        }

        // 변경
        g.changeContent(req.getContent());

        // 변경 후 이력을 history에 저장
        guestbookHistoryRepository.save(
                GuestbookHistory.of(g, g.getUser(), g.getGuestbookContent())
        );

        return GuestbookResponse.from(g);
    }

    // 변경 여부
    private static boolean safeEquals(String a, String b) {
        if (a == null) return b == null;
        return a.equals(b);
    } */



}
