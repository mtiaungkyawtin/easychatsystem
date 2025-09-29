package com.binary.easychatsystem.controller;

import com.binary.easychatsystem.dto.ReadReceiptRequest;
import com.binary.easychatsystem.dto.SendMessageRequest;
import com.binary.easychatsystem.dto.TypingRequest;
import com.binary.easychatsystem.model.ChatMessage;
import com.binary.easychatsystem.service.ChatMessageService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Objects;

/**
 * Controller class for handling chat-related functionality.
 */
@SuppressWarnings("unused")
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate messagingTemplate;

    /**
     * Register user for WebSocket session
     */
    @MessageMapping("/chat.register")
    public void register(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("username", chatMessage.getSenderId());
        headerAccessor.getSessionAttributes().put("userId", chatMessage.getSenderId());
    }

    /**
     * Send message to specific conversation
     */
    @MessageMapping("/chat.send")
    public void sendMessage(@Payload SendMessageRequest messageRequest) {
        ChatMessage savedMessage = chatMessageService.sendMessage(messageRequest);

        // Broadcast to conversation participants
        messagingTemplate.convertAndSend("/topic/conversation." + messageRequest.getConversationId(), savedMessage);

        // Send delivery status to sender
        messagingTemplate.convertAndSendToUser(
                savedMessage.getSenderId(),
                "/queue/message.status",
                Map.of("messageId", savedMessage.getMessageId(), "status", "SENT")
        );
    }

    /**
     * Handle typing indicators
     */
    @MessageMapping("/chat.typing")
    public void handleTyping(@Payload TypingRequest typingRequest) {
        messagingTemplate.convertAndSend(
                "/topic/conversation." + typingRequest.getConversationId() + ".typing",
                typingRequest
        );
    }

    /**
     * Handle message read receipts
     */
    @MessageMapping("/chat.read")
    public void handleReadReceipt(@Payload ReadReceiptRequest readRequest) {
        chatMessageService.markMessagesAsRead(readRequest.getConversationId(), readRequest.getUserId());

        // Notify other participants that messages were read
        messagingTemplate.convertAndSend(
                "/topic/conversation." + readRequest.getConversationId(),
                Map.of("type", "READ_RECEIPT", "readerId", readRequest.getUserId(), "conversationId", readRequest.getConversationId())
        );
    }
}
