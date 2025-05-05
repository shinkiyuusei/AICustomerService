package com.xiaomi.customer.service;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatHistoryConsumer {

    @Autowired
    private ChatHistoryService chatHistoryService;

    @RabbitListener(queues = "chat_history_queue")
    public void receiveChatHistory(String message) {
        JSONObject jsonObject = JSONObject.parseObject(message);
        String userMessage = jsonObject.getString("userMessage");
        String assistantResponse = jsonObject.getString("assistantResponse");

        // 处理接收到的聊天历史数据，例如保存到数据库
        chatHistoryService.saveChatHistory(userMessage, assistantResponse);
    }
}