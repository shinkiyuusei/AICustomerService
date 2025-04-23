package com.xiaomi.customer.service;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

public interface DeepseekChatService {
    JSONObject sendMessage(JSONArray messages) throws Exception;
}