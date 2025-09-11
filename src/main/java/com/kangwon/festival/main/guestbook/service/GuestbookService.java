package com.kangwon.festival.main.guestbook.service;

import com.kangwon.festival.global.annotation.MethodDescription;
import com.kangwon.festival.global.entity.GuestbookHistory;
import com.kangwon.festival.global.entity.GuestbookInfo;
import com.kangwon.festival.global.entity.UserInfo;
import com.kangwon.festival.global.exception.ServiceException;
import com.kangwon.festival.main.guestbook.dto.GuestbookRequest;
import com.kangwon.festival.main.guestbook.dto.GuestbookResponse;
import com.kangwon.festival.main.guestbook.repository.GuestbookHistoryRepository;
import com.kangwon.festival.main.guestbook.repository.GuestbookRepository;
import com.kangwon.festival.main.mypage.repository.MypageUserInfoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.kangwon.festival.global.exception.Code.*;

@Service
@RequiredArgsConstructor
@Transactional
public class GuestbookService {
    private final GuestbookRepository guestbookRepository;
    private final GuestbookHistoryRepository guestbookHistoryRepository;
    private final MypageUserInfoRepository userInfoRepository;

    @Transactional
    @MethodDescription(description = "방명록을 등록합니다.")
    public GuestbookResponse create(GuestbookRequest req) {
        UserInfo writer = userInfoRepository.findById(req.getUserId())
                .orElseThrow(() -> new ServiceException(CAN_NOT_FIND_USER));

        GuestbookInfo saved = guestbookRepository.save(
                GuestbookInfo.create(
                        writer,
                        req.isAnonymous(),
                        req.getTitle(),
                        req.getContent()
                )
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
    @MethodDescription(description = "내가 쓴 방명록을 수정합니다. (이전 내용은 history로 보관)")
    public GuestbookResponse updateMyGuestbook(Long userId, Long guestbookId, GuestbookRequest req) {
        GuestbookInfo g = guestbookRepository.findByGuestbookIdAndGuestbookIsDeletedFalse(guestbookId)
                .orElseThrow(() -> new ServiceException(CAN_NOT_FIND_RESOURCE));

        // 권한 체크
        if (!g.getUser().getUserId().equals(userId)) {
            throw new ServiceException(ACCESS_DENIED, "본인이 작성한 방명록만 수정할 수 있습니다.");
        }

        // 변경 전 이력을 history에 저장(+ DB 변경하면 유저id, 익명 여부 추가하기)
        guestbookHistoryRepository.save(
                GuestbookHistory.of(g, g.getGuestbookTitle(), g.getGuestbookContent())
        );

        // 변경
        g.changeTitle(req.getTitle());
        g.changeContent(req.getContent());
        // 익명 변경 추가

        return GuestbookResponse.from(g);
    }

    @Transactional
    @MethodDescription(description = "내가 쓴 방명록을 삭제합니다.")
    public void deleteMyGuestbook(Long userId, Long guestbookId) {
        GuestbookInfo g = guestbookRepository.findByGuestbookIdAndGuestbookIsDeletedFalse(guestbookId)
                .orElseThrow(() -> new ServiceException(CAN_NOT_FIND_RESOURCE));

        if (!g.getUser().getUserId().equals(userId)) {
            throw new ServiceException(ACCESS_DENIED, "본인이 작성한 방명록만 삭제할 수 있습니다.");
        }

        g.deleted();
    }


}
