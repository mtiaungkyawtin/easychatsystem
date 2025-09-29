package com.binary.easychatsystem.repository;

import com.binary.easychatsystem.model.ChatConversation;
import com.binary.easychatsystem.model.ChatConversationParticipant;
import com.binary.easychatsystem.model.ChatConversationParticipantId;
import com.binary.easychatsystem.model.ChatMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findByConversation_ConversationIdOrderByCreatedAtDesc(Long conversationId, Pageable pageable);

    @Modifying
    @Query("UPDATE ChatMessage m SET m.status = 'READ', m.readAt = :readAt WHERE m.conversation.conversationId = :conversationId AND m.recipientId = :recipientId AND m.status != 'READ'")
    void markMessagesAsRead(@Param("conversationId") Long conversationId, @Param("recipientId") String recipientId, @Param("readAt") LocalDateTime readAt);

    default void markMessagesAsRead(Long conversationId, String recipientId) {
        markMessagesAsRead(conversationId, recipientId, LocalDateTime.now());
    }
}




