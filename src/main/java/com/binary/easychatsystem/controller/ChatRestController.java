package com.binary.easychatsystem.controller;

import com.binary.easychatsystem.dto.ConversationDTO;
import com.binary.easychatsystem.dto.CreateConversationRequest;
import com.binary.easychatsystem.model.ChatMessage;
import com.binary.easychatsystem.service.ChatConversationService;
import com.binary.easychatsystem.service.ChatMessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class ChatRestController {

    private final ChatMessageService chatMessageService;
    private final ChatConversationService conversationService;

    // Get user conversations
    @GetMapping("/conversations")
    public ResponseEntity<List<ConversationDTO>> getUserConversations(
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = extractUserId(userDetails);
        List<ConversationDTO> conversations = conversationService.getUserConversations(userId);
        return ResponseEntity.ok(conversations);
    }

    // Create conversation (direct or group)
    @PostMapping("/conversations")
    public ResponseEntity<ConversationDTO> createConversation(
            @RequestBody CreateConversationRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        Long creatorId = extractUserId(userDetails);
        ConversationDTO conversation = conversationService.createConversation(request, creatorId);
        return ResponseEntity.ok(conversation);
    }

    // Get conversation messages
    @GetMapping("/conversations/{conversationId}")
    public ResponseEntity<List<ChatMessage>> getConversationMessages(
            @PathVariable Long conversationId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @AuthenticationPrincipal UserDetails userDetails) {

        Long userId = extractUserId(userDetails);
        List<ChatMessage> messages = chatMessageService.getConversationMessages(conversationId, userId, page, size);
        return ResponseEntity.ok(messages);
    }

    private Long extractUserId(UserDetails userDetails) {
        // Extract user ID from authentication - you'll need to implement this based on your User entity
        return 1L; // Placeholder
    }
}