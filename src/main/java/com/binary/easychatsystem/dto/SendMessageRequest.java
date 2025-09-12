package com.binary.easychatsystem.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SendMessageRequest {
    private Long conversationId;
    private Long senderId;
    private Long recipientId; // optional for DIRECT; null for GROUP
    private String content;
    private String mediaUrl;
    private String mediaMime;
    private Long mediaSize;
}
