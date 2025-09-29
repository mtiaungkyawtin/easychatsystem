package com.binary.easychatsystem.service;

import com.binary.easychatsystem.dto.ConversationDTO;
import com.binary.easychatsystem.dto.CreateConversationRequest;

import java.util.List;

public interface ChatConversationService {
    List<ConversationDTO> getUserConversations(Long userId);
    ConversationDTO createConversation(CreateConversationRequest request, Long creatorId);
}
