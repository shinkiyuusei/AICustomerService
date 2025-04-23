package com.xiaomi.customer.domain;

import java.util.Arrays;

public class ChatRequest {
    private String model;
    private Message[] messages;

    // Getter 和 Setter 方法
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Message[] getMessages() {
        return messages;
    }

    public void setMessages(Message[] messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "ChatRequest{" +
                "model='" + model + '\'' +
                ", messages=" + Arrays.toString(messages) +
                '}';
    }
}
