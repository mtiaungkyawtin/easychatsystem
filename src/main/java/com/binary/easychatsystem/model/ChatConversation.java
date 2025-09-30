package com.binary.easychatsystem.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Column(nullable = false)
    private Long createdBy;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    // One-to-Many: conversation contains many messages
    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("conversation-messages")
    private List<ChatMessage> messages = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = LocalDateTime.now();
    }

    public enum ConversationType {
        DIRECT, GROUP
    }
}
