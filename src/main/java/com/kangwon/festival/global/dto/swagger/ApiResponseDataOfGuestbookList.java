package com.kangwon.festival.global.dto.swagger;

import com.kangwon.festival.domain.guestbook.dto.GuestbookResponse;
import com.kangwon.festival.global.dto.ApiResponseData;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(name = "ApiResponseDataOfGuestbookList", description = "방명록 목록 응답")
public class ApiResponseDataOfGuestbookList extends ApiResponseData<List<GuestbookResponse>> {}