package com.xiaomi.customer.service;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.xiaomi.customer.domain.KnowledgeBase;

public interface KnowledgeBaseService {


    JSONArray getKnowledgeBase();

    JSONObject addKnowledge(KnowledgeBase knowledgeBase);

    JSONObject updateKnowledge(Long id, KnowledgeBase knowledgeBase);


    JSONObject deleteKnowledge(Long id);

    JSONObject searchKnowledgeBase(String query);
}