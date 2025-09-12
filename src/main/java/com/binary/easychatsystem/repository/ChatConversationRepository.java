package com.binary.easychatsystem.repository;

import com.binary.easychatsystem.model.ChatConversation;
import com.binary.easychatsystem.model.ChatConversation.ConversationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatConversationRepository extends JpaRepository<ChatConversation, Long> {
    List<ChatConversation> findByType(ConversationType type);
}
