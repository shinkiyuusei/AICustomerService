package com.xiaomi.knowledge;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

public interface KnowledgeBaseService {


    JSONArray getKnowledgeBase();

    JSONObject addKnowledge(KnowledgeBase knowledgeBase);

    JSONObject updateKnowledge(Long id, KnowledgeBase knowledgeBase);


    JSONObject deleteKnowledge(Long id);

    JSONObject searchKnowledgeBase(String query);
}