package com.binary.easychatsystem.repository;

import com.binary.easychatsystem.model.ChatConversationParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatConversationParticipantRepository extends JpaRepository<ChatConversationParticipant, Long> {
    boolean existsByConversationIdAndUserId(Long conversationId, Long userId);
    List<ChatConversationParticipant> findByUserId(Long userId);
    List<ChatConversationParticipant> findByConversationId(Long conversationId);
}
