package com.binary.easychatsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TypingRequest {
    private Long conversationId;
    private Long userId;
    private Boolean isTyping;
}
