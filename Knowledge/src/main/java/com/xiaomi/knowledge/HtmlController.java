package com.xiaomi.knowledge;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HtmlController {

    @GetMapping("/knowledgeBase")
    public String search() {
        return "forward:/knowledgeBase.html"; // 转发到 chat.html
    }
}