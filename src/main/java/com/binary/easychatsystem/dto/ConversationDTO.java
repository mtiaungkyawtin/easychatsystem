package com.binary.easychatsystem.dto;

import com.binary.easychatsystem.model.ChatConversation;
import com.binary.easychatsystem.model.ChatMessage;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter @NoArgsConstructor @AllArgsConstructor
public class ConversationDTO {
    private Long conversationId;
    private ChatConversation.ConversationType type;
    private String title;
    private LocalDateTime createdAt;
    private ChatMessage lastMessage;
    private Integer unreadCount;
}
