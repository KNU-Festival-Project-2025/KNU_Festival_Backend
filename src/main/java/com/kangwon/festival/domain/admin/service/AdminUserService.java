package com.kangwon.festival.domain.admin.service;

import com.kangwon.festival.global.annotation.MethodDescription;
import com.kangwon.festival.global.exception.ServiceException;
import com.kangwon.festival.domain.admin.dto.UserBlockedResponse;
import com.kangwon.festival.domain.admin.dto.UserReportResponse;
import com.kangwon.festival.domain.admin.repository.AdminUserBlockRepository;
import com.kangwon.festival.domain.admin.repository.AdminUserInfoRepository;
import com.kangwon.festival.domain.admin.repository.AdminUserReportRepository;
import com.kangwon.festival.global.entity.UserInfo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

import static com.kangwon.festival.global.exception.Code.*;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final AdminUserReportRepository userReportRepository;
    private final AdminUserBlockRepository userBlockRepository;
    private final AdminUserInfoRepository userInfoRepository;

    @MethodDescription(description = "모든 신고내역을 조회합니다.")
    public List<UserReportResponse> getAllReports() {
        return userReportRepository.findAll()
                .stream()
                .map(UserReportResponse::from)
                .toList();
    }

    @MethodDescription(description = "특정 유저의 신고내역을 조회합니다.")
    public List<UserReportResponse> getReportsByUser(Long reportedUserId) {
        UserInfo reportedUser = userInfoRepository.findById(reportedUserId)
                .orElseThrow(() -> new ServiceException(CAN_NOT_FIND_USER));

        return userReportRepository.findByReportedUser(reportedUser)
                .stream()
                .map(UserReportResponse::from)
                .toList();
    }

    @MethodDescription(description = "모든 차단내역을 조회합니다.")
    public List<UserBlockedResponse> getAllBlocks() {
        return userBlockRepository.findAll()
                .stream()
                .map(UserBlockedResponse::from)
                .toList();
    }

    @MethodDescription(description = "특정 유저의 차단내역을 조회합니다.")
    public List<UserBlockedResponse> getBlocksByBlockedUser(Long blockedUserId) {
        UserInfo blockedUser = userInfoRepository.findById(blockedUserId)
                .orElseThrow(() -> new ServiceException(CAN_NOT_FIND_BLOCKED_USER));
        return userBlockRepository.findByBlocked(blockedUser)
                .stream()
                .map(UserBlockedResponse::from)
                .toList();
    }

    @MethodDescription(description = "전체 유저를 조회합니다.")
    public List<UserInfo> getAllUser() {
        return userInfoRepository.findAll();
    }

    @MethodDescription(description = "특정 유저를 조회합니다.")
    public UserInfo getUser(Long userId) {
        return userInfoRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(CAN_NOT_FIND_USER));
    }

    @Transactional
    @MethodDescription(description = "유저 이용을 차단합니다.")
    public void banUser(Long userId) {
        UserInfo user = userInfoRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(CAN_NOT_FIND_USER));

        if (user.isBanned()) {
            throw new ServiceException(USER_ALREADY_BANNED);
        }

        user.ban();
    }

}
