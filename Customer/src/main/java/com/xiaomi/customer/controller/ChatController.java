package com.xiaomi.customer.controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.xiaomi.customer.service.ChatHistoryService;
import com.xiaomi.customer.service.DeepseekChatService;
import com.xiaomi.customer.service.KnowledgeBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/chat")
public class ChatController {

    @Autowired
    private DeepseekChatService deepseekChatService;

    @Autowired
    private ChatHistoryService chatHistoryService;

    @Autowired
    private KnowledgeBaseService knowledgeBaseService;

    @PostMapping("/send")
    public JSONObject sendMessage(@RequestBody JSONObject request) {
        try {
            JSONArray messages = request.getJSONArray("messages");
            String userMessage = messages.getJSONObject(0).getString("content");

            // 检查知识库是否有相似答案
            JSONObject knowledgeResponse = knowledgeBaseService.searchSimilarKnowledgeBase(userMessage, 0.8);
            if (knowledgeResponse != null && knowledgeResponse.containsKey("answer")) {
                return createSuccessResponse(knowledgeResponse.getString("answer"));
            }

            // 检查历史记录是否有相似答案
            try {
                Map<String, Object> chatHistory = chatHistoryService.findSimilarChatHistory(userMessage, 0.8);
                if (chatHistory != null &&!chatHistory.isEmpty()) {
                    return createSuccessResponse((String) chatHistory.get("assistant_response"));
                }
            } catch (Exception e) {
                // 处理空结果的情况，继续执行后续逻辑
            }

            // 调用外部服务获取回复
            try {
                JSONObject responseJson = deepseekChatService.sendMessage(messages);
                if (responseJson != null && responseJson.containsKey("choices")) {
                    JSONArray choices = responseJson.getJSONArray("choices");
                    if (choices != null &&!choices.isEmpty()) {
                        JSONObject firstChoice = choices.getJSONObject(0);
                        if (firstChoice != null && firstChoice.containsKey("message")) {
                            JSONObject assistantResponse = firstChoice.getJSONObject("message");
                            if (assistantResponse != null && assistantResponse.containsKey("content")) {
                                String assistantResponseContent = assistantResponse.getString("content");

                                // 检查并截断 assistantResponseContent 的长度
                                int maxLength = 1000;
                                if (assistantResponseContent.length() > maxLength) {
                                    assistantResponseContent = assistantResponseContent.substring(0, maxLength);
                                }

                                // 保存到数据库
                                chatHistoryService.saveChatHistory(userMessage, assistantResponseContent);

                                // 发送聊天历史到 RabbitMQ 队列
                                chatHistoryService.sendChatHistoryToQueue(userMessage, assistantResponseContent);

                                // 返回结果
                                return createSuccessResponse(assistantResponseContent);
                            }
                        }
                    }
                }
            } catch (RuntimeException e) {
                return createErrorResponse("请求外部服务失败：" + e.getMessage());
            } catch (Exception e) {
                return createErrorResponse("处理外部服务响应失败：" + e.getMessage());
            }

            // 如果没有找到任何结果，返回默认响应
            return createSuccessResponse("No response found.");
        } catch (Exception e) {
            return createErrorResponse(e.getMessage());
        }
    }

    private JSONObject createSuccessResponse(String content) {
        JSONObject result = new JSONObject();
        result.put("status", "success");
        result.put("data", new JSONObject().fluentPut("content", content));
        return result;
    }

    private JSONObject createErrorResponse(String message) {
        JSONObject errorResult = new JSONObject();
        errorResult.put("status", "error");
        errorResult.put("message", message);
        return errorResult;
    }

    @GetMapping("/history")
    public List<Map<String, String>> getChatHistory() {
        List<Map<String, Object>> rawHistory = chatHistoryService.getChatHistory();
        List<Map<String, String>> result = new ArrayList<>();

        for (Map<String, Object> entry : rawHistory) {
            Map<String, String> historyEntry = new HashMap<>();

            // 处理 user_message 字段
            String userMessage = Optional.ofNullable(entry.get("user_message"))
                    .map(Object::toString)
                    .orElse("");
            historyEntry.put("user_message", userMessage);

            // 处理 assistant_response 字段
            String assistantResponse = Optional.ofNullable(entry.get("assistant_response"))
                    .map(Object::toString)
                    .orElse("");
            historyEntry.put("assistant_response", assistantResponse);

            result.add(historyEntry);
        }
        return result;
    }
}