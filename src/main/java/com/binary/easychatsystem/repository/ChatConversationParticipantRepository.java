package com.binary.easychatsystem.repository;

import com.binary.easychatsystem.model.ChatConversationParticipant;
import com.binary.easychatsystem.model.ChatConversationParticipantId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatConversationParticipantRepository extends JpaRepository<ChatConversationParticipant, ChatConversationParticipantId> {

    boolean existsByConversationIdAndUserId(Long conversationId, Long userId);

    // Add more descriptive method names
    default boolean isUserParticipant(Long conversationId, Long userId) {
        return existsByConversationIdAndUserId(conversationId, userId);
    }

//    List<ChatConversationParticipant> findByConversationId(Long conversationId);
}
