package com.xiaomi.customer.service.serviceImpl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.xiaomi.customer.service.DeepseekChatService;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicClassicHttpRequest;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class DeepseekChatServiceImpl implements DeepseekChatService {

    private static final String API_KEY = "sk-8cdb146e42be4f489fede4f6d8bffdb9";
    private static final String BASE_URL = "https://api.deepseek.com/v1";

    @Override
    public JSONObject sendMessage(JSONArray messages) throws Exception {
        // 创建请求的 JSON 数据
        JSONObject payload = new JSONObject();
        payload.put("model", "deepseek-chat");// 模型名称
        payload.put("messages", messages);// 消息列表

        // 初始化 HttpClient 和 HttpPost
        CloseableHttpClient client = HttpClients.createDefault();
        ClassicHttpRequest postMethod = new BasicClassicHttpRequest("POST", BASE_URL + "/chat/completions");

        // 设置请求头
        postMethod.setHeader("Content-Type", "application/json");
        postMethod.setHeader("Authorization", "Bearer " + API_KEY);

        // 设置请求体为 JSON 数据
        org.apache.hc.core5.http.io.entity.StringEntity entity =
                new org.apache.hc.core5.http.io.entity.StringEntity(payload.toString(), StandardCharsets.UTF_8);
        ((BasicClassicHttpRequest) postMethod).setEntity(entity);

        // 执行请求
        org.apache.hc.core5.http.ClassicHttpResponse response = (org.apache.hc.core5.http.ClassicHttpResponse) client.execute(postMethod);
        int statusCode = response.getCode();
        if (statusCode != 200) {
            throw new RuntimeException("请求失败，状态码：" + statusCode);
        }

        // 解析响应
        String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        return JSONObject.parseObject(responseBody);
    }
}