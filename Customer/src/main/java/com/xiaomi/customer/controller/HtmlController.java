package com.xiaomi.customer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HtmlController {

    @GetMapping("/chat")
    public String chat() {
        return "forward:/chat.html"; // 转发到 chat.html
    }

}