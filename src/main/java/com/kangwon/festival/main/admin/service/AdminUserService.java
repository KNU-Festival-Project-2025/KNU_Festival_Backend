package com.kangwon.festival.main.admin.service;

import com.kangwon.festival.global.exception.Code;
import com.kangwon.festival.global.exception.ServiceException;
import com.kangwon.festival.global.entity.UserBlock;
import com.kangwon.festival.global.entity.UserReport;
import com.kangwon.festival.main.admin.dto.UserBlockedResponse;
import com.kangwon.festival.main.admin.dto.UserReportResponse;
import com.kangwon.festival.main.admin.repository.AdminUserBlockRepository;
import com.kangwon.festival.main.admin.repository.AdminUserInfoRepository;
import com.kangwon.festival.main.admin.repository.AdminUserReportRepository;
import com.kangwon.festival.global.entity.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final AdminUserReportRepository userReportRepository;
    private final AdminUserBlockRepository userBlockRepository;
    private final AdminUserInfoRepository userInfoRepository;

    // 모든 신고 내역
    public List<UserReportResponse> getAllReports() {
        return userReportRepository.findAll()
                .stream()
                .map(report -> new UserReportResponse(
                        report.getUserReportId(),
                        report.getReporter().getUserId(),
                        report.getReporter().getUserNickname(),
                        report.getReportReason(),
                        report.getReportedUser().getUserId(),
                        report.getReportedUser().getUserNickname(),
                        report.getReportedUser().isBanned(),
                        report.getCreatedDateTime()
                ))
                .toList();
    }

    // 특정 유저의 신고 내역
    public List<UserReportResponse> getReportsByUser(int reportedUserId) {
        UserInfo reportedUser = userInfoRepository.findById(reportedUserId)
                .orElseThrow(() -> new ServiceException(Code.CAN_NOT_FIND_USER));

        return userReportRepository.findByReportedUser(reportedUser)
                .stream()
                .map(report -> new UserReportResponse(
                        report.getUserReportId(),
                        report.getReporter().getUserId(),
                        report.getReporter().getUserNickname(),
                        report.getReportReason(),
                        report.getReportedUser().getUserId(),
                        report.getReportedUser().getUserNickname(),
                        report.getReportedUser().isBanned(),
                        report.getCreatedDateTime()
                ))
                .toList();
    }

    // 모든 차단 내역
    public List<UserBlockedResponse> getAllBlocks() {
        return userBlockRepository.findAll()
                .stream()
                .map(block -> new UserBlockedResponse(
                        block.getUserBlockId(),
                        block.getBlocker().getUserId(),
                        block.getBlocker().getUserNickname(),
                        block.getBlocked().getUserId(),
                        block.getBlocked().getUserNickname(),
                        block.getBlocked().isBanned(),
                        block.getCreatedDateTime()
                ))
                .toList();
    }

    // 특정 유저 차단 내역
    public List<UserBlockedResponse> getBlocksByBlockedUser(int blockedUserId) {
        UserInfo blockedUser = userInfoRepository.findById(blockedUserId)
                .orElseThrow(() -> new ServiceException(Code.CAN_NOT_FIND_BLOCKED_USER));
        return userBlockRepository.findByBlocked(blockedUser)
                .stream()
                .map(block -> new UserBlockedResponse(
                        block.getUserBlockId(),
                        block.getBlocker().getUserId(),
                        block.getBlocker().getUserNickname(),
                        block.getBlocked().getUserId(),
                        block.getBlocked().getUserNickname(),
                        block.getBlocked().isBanned(),
                        block.getCreatedDateTime()
                ))
                .toList();
    }

    // 전체 유저 조회
    public List<UserInfo> getAllUser() {
        return userInfoRepository.findAll();
    }

    // 특정 유저 조회
    public UserInfo getUser(int userId) {
        return userInfoRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(Code.CAN_NOT_FIND_USER));
    }

    // 유저 이용 차단
    public void banUser(int userId) {
        UserInfo user = userInfoRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(Code.CAN_NOT_FIND_USER));

        if (user.isBanned()) {
            throw new ServiceException(Code.USER_ALREADY_BANNED);
        }

        user.setBanned(true);
        userInfoRepository.save(user);
    }

}
