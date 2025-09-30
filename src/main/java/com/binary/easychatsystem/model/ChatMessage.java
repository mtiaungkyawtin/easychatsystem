package com.binary.easychatsystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents a chat message in the chat application.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "chat_messages",
        indexes = {
                @Index(name = "ix_message_conversation_created_at", columnList = "conversation_id, createdAt"),
                @Index(name = "ix_message_sender_created_at", columnList = "sender_id, createdAt"),
                @Index(name = "ix_message_status", columnList = "status")
        })
public class ChatMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long messageId;
    @Column(columnDefinition = "text")
    private String content;
    @Enumerated(EnumType.STRING)
    @Column(length = 16, nullable = false)
    private MediaType mediaType;
    @Column(length = 1024)
    private String mediaUrl;
    @Column(length = 128)
    private String mediaMime;
    private Long mediaSize;
    private String senderId;
    private String recipientId;
    @Enumerated(EnumType.STRING)
    @Column(length = 16, nullable = false)
    private MessageType type;
    @Enumerated(EnumType.STRING)
    @Column(length = 16, nullable = false)
    private MessageStatus status;
    @Column(nullable = false)
    private Boolean isDeleted = false;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime readAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id", nullable = false)
    @JsonBackReference("conversation-messages")
    private ChatConversation conversation;

    /**
     * Enum representing the type of the chat message.
     */
    public enum MessageType {
        CHAT, LEAVE, JOIN
    }

    public enum MediaType {
        IMAGE, VIDEO, AUDIO, DOCUMENT, TEXT
    }

    /**
     * Enum representing the type of the chat message.
     */
    public enum MessageStatus {
        SENT, DELIVERED, READ
    }

    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = LocalDateTime.now();
        if (status == null) status = MessageStatus.SENT;
        if (type == null) type = MessageType.CHAT;
        if (mediaType == null) mediaType = MediaType.TEXT;
        if (isDeleted == null) isDeleted = false;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
