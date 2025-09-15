package com.kangwon.festival.global.dto.swagger;


import com.kangwon.festival.domain.guestbook.dto.GuestbookResponse;
import com.kangwon.festival.global.dto.ApiResponseData;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ApiResponseDataOfGuestbookResponse", description = "단일 방명록 응답")
public class ApiResponseDataOfGuestbookResponse extends ApiResponseData<GuestbookResponse> {}
