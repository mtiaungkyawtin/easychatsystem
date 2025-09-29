package com.binary.easychatsystem.repository;

import com.binary.easychatsystem.model.ChatConversation;
import com.binary.easychatsystem.model.ChatConversation.ConversationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatConversationRepository extends JpaRepository<ChatConversation, Long> {

    @Query("SELECT DISTINCT c FROM ChatConversation c JOIN c.participants p WHERE p.userId = :userId ORDER BY c.createdAt DESC")
    List<ChatConversation> findUserConversations(@Param("userId") Long userId);
}
