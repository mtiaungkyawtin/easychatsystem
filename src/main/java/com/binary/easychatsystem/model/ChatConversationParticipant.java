package com.binary.easychatsystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@IdClass(ChatConversationParticipantId.class)
@Table(name = "chat_conversation_participants",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_conversation_user", columnNames = {"conversation_id", "user_id"})
        },
        indexes = {
                @Index(name = "ix_participant_conversation", columnList = "conversation_id"),
                @Index(name = "ix_participant_user", columnList = "user_id")
        })
public class ChatConversationParticipant {
    @Id
    private Long conversationId;
    @Id
    private Long userId;

    @Column(nullable = false)
    private LocalDateTime joinedAt;

    @PrePersist
    public void prePersist() {
        if (joinedAt == null) joinedAt = LocalDateTime.now();
    }
}
