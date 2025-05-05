package com.xiaomi.customer.service;

import java.util.List;
import java.util.Map;

public interface ChatHistoryService {
    List<Map<String, Object>> getChatHistory();
    void saveChatHistory(String userMessage, String assistantResponse);
    Map<String, Object> findByUserMessage(String userMessage);
    Map<String, Object> findSimilarChatHistory(String userMessage, double threshold);
}