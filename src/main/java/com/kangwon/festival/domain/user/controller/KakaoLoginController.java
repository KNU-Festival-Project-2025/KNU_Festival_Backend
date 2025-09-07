package com.kangwon.festival.domain.user.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class KakaoLoginController {
        @Value("${kakao.client_id}")
        private String client_id;

        @Value("${kakao.redirect_uri}")
        private String redirect_uri;

        @GetMapping
        public String loginPage(Model model) {
            String encoded = URLEncoder.encode(redirect_uri, StandardCharsets.UTF_8);
            String location = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id="+client_id+"&redirect_uri="+encoded;
            model.addAttribute("location", location);

            return "login";
        }
}
