package com.xiaomi.knowledge;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/knowledgeBase")
@CrossOrigin(origins = "*") // 允许所有来源的跨域请求
public class KnowledgeBaseController {

    @Autowired
    private KnowledgeBaseService knowledgeBaseService;

    @GetMapping("/all")
    public JSONArray getAllKnowledge() {
        return knowledgeBaseService.getKnowledgeBase();
    }

    @GetMapping("/search")
    public JSONObject searchKnowledge(@RequestParam String query) {
        return knowledgeBaseService.searchKnowledgeBase(query);
    }
    @PostMapping("/add")
    public JSONObject addKnowledge(@RequestBody KnowledgeBase knowledgeBase) {
        return knowledgeBaseService.addKnowledge(knowledgeBase);
    }

    @PutMapping("/update/{id}")
    public JSONObject updateKnowledge(@PathVariable Long id, @RequestBody KnowledgeBase knowledgeBase) {
        return knowledgeBaseService.updateKnowledge(id, knowledgeBase);
    }

    @DeleteMapping("/delete/{id}")
    public JSONObject deleteKnowledge(@PathVariable Long id) {
        return knowledgeBaseService.deleteKnowledge(id);
    }
}