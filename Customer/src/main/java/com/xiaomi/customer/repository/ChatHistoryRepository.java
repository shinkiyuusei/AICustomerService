package com.xiaomi.customer.repository;

import com.xiaomi.customer.domain.ChatHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Map;

//操作数据库

public interface ChatHistoryRepository extends JpaRepository<ChatHistory, Long> {
    @Query("SELECT new map(ch.userMessage as user_message, ch.assistantResponse as assistant_response) FROM ChatHistory ch WHERE ch.userMessage = :userMessage")
    Map<String, Object> findByUserMessage(@Param("userMessage") String userMessage);
}