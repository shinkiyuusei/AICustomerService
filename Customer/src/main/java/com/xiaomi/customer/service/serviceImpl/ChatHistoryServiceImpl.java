package com.xiaomi.customer.service.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaomi.customer.service.ChatHistoryService;
import org.apache.commons.lang.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChatHistoryServiceImpl implements ChatHistoryService {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Map<String, Object>> getChatHistory() {
        try {
            String sql = "SELECT COALESCE(user_message, '') AS user_message, COALESCE(assistant_response, '') AS assistant_response FROM logindemo.chat_history";
            return jdbcTemplate.queryForList(sql);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch chat history from database", e);
        }
    }

    @Override
    public void saveChatHistory(String userMessage, String assistantResponse) {
        try {
            String sql = "INSERT INTO logindemo.chat_history (user_message, assistant_response) VALUES (?, ?)";
            jdbcTemplate.update(sql, userMessage, assistantResponse);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to save chat history to database", e);
        }
    }

    @Override
    public Map<String, Object> findByUserMessage(String userMessage) {
        // 查询数据库
        String sql = "SELECT user_message, assistant_response FROM logindemo.chat_history WHERE user_message = ?";
        try {
            return jdbcTemplate.queryForMap(sql, userMessage);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Map<String, Object> findSimilarChatHistory(String userMessage, double threshold) {// 查询与给定用户消息相似的历史记录
        List<Map<String, Object>> chatHistoryList = getChatHistory();
        Map<String, Object> mostSimilar = chatHistoryList.stream()
                .max((ch1, ch2) -> {
                    String msg1 = (String) ch1.get("user_message");
                    String msg2 = (String) ch2.get("user_message");
                    double sim1 = StringUtils.getLevenshteinDistance(userMessage, msg1);
                    double sim2 = StringUtils.getLevenshteinDistance(userMessage, msg2);
                    return Double.compare(sim2, sim1);
                })
                .orElse(null);
        if (mostSimilar != null) {
            String msg = (String) mostSimilar.get("user_message");
            double similarity = 1 - (double) StringUtils.getLevenshteinDistance(userMessage, msg) / Math.max(userMessage.length(), msg.length());
            if (similarity >= threshold) {
                return mostSimilar;
            }
        }
        return null;
    }
}