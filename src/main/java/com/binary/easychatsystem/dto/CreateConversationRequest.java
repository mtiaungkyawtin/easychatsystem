package com.binary.easychatsystem.dto;

import com.binary.easychatsystem.model.ChatConversation;
import lombok.*;

import java.util.List;

@Getter
@Setter @NoArgsConstructor @AllArgsConstructor
public class CreateConversationRequest {
    private ChatConversation.ConversationType type;
    private String title; // For GROUP conversations
    private List<Long> participantIds; // For GROUP or DIRECT conversations
    private Long recipientId; // For DIRECT conversations only
}
