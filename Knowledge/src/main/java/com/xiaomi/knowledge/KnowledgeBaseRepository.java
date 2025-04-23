package com.xiaomi.knowledge;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface KnowledgeBaseRepository extends JpaRepository<KnowledgeBase, Long> {

    @Query("SELECT kb FROM KnowledgeBase kb WHERE LOWER(kb.question) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<KnowledgeBase> findByQuestionContainingIgnoreCase(@Param("query") String query);
}