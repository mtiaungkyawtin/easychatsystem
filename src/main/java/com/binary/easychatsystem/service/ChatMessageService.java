package com.binary.easychatsystem.service;

import com.binary.easychatsystem.dto.SendMessageRequest;
import com.binary.easychatsystem.model.ChatMessage;

import java.util.List;

public interface ChatMessageService {
    List<ChatMessage> findAllMessage();
    ChatMessage findMessageByID(Long id);
    void addMessage(ChatMessage data);
    void deleteAll();
    void deleteById(Long id);

    ChatMessage sendMessage(SendMessageRequest request);
    List<ChatMessage> getConversationMessages(Long conversationId, Long userId, int page, int size);
    void markMessagesAsRead(Long conversationId, Long userId);
}
