package com.binary.easychatsystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "chat_conversations",
        indexes = {
                @Index(name = "ix_conversation_type", columnList = "type"),
                @Index(name = "ix_conversation_created_at", columnList = "createdAt")
        })
public class ChatConversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "conversation_id")
    private Long conversationId;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private ConversationType type;
    private String title;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_user_id")
    private User createdBy;
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = LocalDateTime.now();
    }

    public enum ConversationType {
        DIRECT, GROUP
    }
}
