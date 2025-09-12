package com.binary.easychatsystem.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ChatConversationParticipantId implements Serializable {
    private Long conversationId;
    private Long userId;
}
