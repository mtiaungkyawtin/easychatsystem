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

//    @Query("SELECT DISTINCT c FROM ChatConversation c JOIN c.participants p WHERE p.userId = :userId ORDER BY c.createdAt DESC")
//    List<ChatConversation> findUserConversations(@Param("userId") Long userId);

    @Query(value = """
        SELECT c.conversation_id AS conversation_id,
               c.type AS type,
               c.title AS title,
               c.created_by AS created_by,
               c.created_at AS created_at
        FROM chat_conversations c
        JOIN chat_conversation_participants cp ON c.conversation_id = cp.conversation_id
        WHERE cp.user_id = :userId
        ORDER BY c.created_at DESC
    """, nativeQuery = true)
    List<ChatConversation> getConversationsByUserId(@Param("userId") Long userId);
}
