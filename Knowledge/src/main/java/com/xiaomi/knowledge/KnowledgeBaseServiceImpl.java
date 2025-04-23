package com.xiaomi.knowledge;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KnowledgeBaseServiceImpl implements KnowledgeBaseService {

    @Autowired
    private KnowledgeBaseRepository knowledgeBaseRepository;

    @Override
    public JSONArray getKnowledgeBase() {
        JSONArray result = new JSONArray();
        List<KnowledgeBase> knowledgeList = knowledgeBaseRepository.findAll();

        for (KnowledgeBase knowledge : knowledgeList) {
            JSONObject item = new JSONObject();
            item.put("id", knowledge.getId());
            item.put("question", knowledge.getQuestion());
            item.put("answer", knowledge.getAnswer());
            result.add(item);
        }

        return result;
    }

    @Override
    public JSONObject addKnowledge(KnowledgeBase knowledgeBase) {
        JSONObject result = new JSONObject();
        knowledgeBaseRepository.save(knowledgeBase);
        result.put("status", "success");
        result.put("message", "Knowledge added successfully");
        return result;
    }

    @Override
    public JSONObject updateKnowledge(Long id, KnowledgeBase knowledgeBase) {
        JSONObject result = new JSONObject();
        KnowledgeBase existingKnowledge = knowledgeBaseRepository.findById(id).orElse(null);
        if (existingKnowledge != null) {
            existingKnowledge.setQuestion(knowledgeBase.getQuestion());
            existingKnowledge.setAnswer(knowledgeBase.getAnswer());
            knowledgeBaseRepository.save(existingKnowledge);
            result.put("status", "success");
            result.put("message", "Knowledge updated successfully");
        } else {
            result.put("status", "error");
            result.put("message", "Knowledge not found");
        }
        return result;
    }

    @Override
    public JSONObject deleteKnowledge(Long id) {
        JSONObject result = new JSONObject();
        if (knowledgeBaseRepository.existsById(id)) {
            knowledgeBaseRepository.deleteById(id);
            result.put("status", "success");
            result.put("message", "Knowledge deleted successfully");
        } else {
            result.put("status", "error");
            result.put("message", "Knowledge not found");
        }
        return result;
    }

    @Override
    public JSONObject searchKnowledgeBase(String query) {
        JSONObject result = new JSONObject();
        List<KnowledgeBase> knowledgeList = knowledgeBaseRepository.findByQuestionContainingIgnoreCase(query);

        if (!knowledgeList.isEmpty()) {
            // 返回第一个匹配的结果
            KnowledgeBase knowledge = knowledgeList.get(0);
            result.put("question", knowledge.getQuestion());
            result.put("answer", knowledge.getAnswer());
        }

        return result;
    }
}