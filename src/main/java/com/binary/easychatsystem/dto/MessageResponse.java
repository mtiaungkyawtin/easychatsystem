package com.binary.easychatsystem.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageResponse {
    private Long messageId;
    private Long conversationId;
    private Long senderId;
    private Long recipientId;
    private String content;
    private String mediaUrl;
    private String mediaMime;
    private Long mediaSize;
    private String mediaType;
    private String status;
    private LocalDateTime createdAt;
}