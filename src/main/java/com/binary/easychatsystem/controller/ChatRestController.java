package com.binary.easychatsystem.controller;

import com.binary.easychatsystem.model.ChatMessage;
import com.binary.easychatsystem.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class ChatRestController {
    private final ChatMessageService chatMessageService;

    // Get all messages with pagination
    @GetMapping
    public ResponseEntity<List<ChatMessage>> getAllMessages() {
        return ResponseEntity.ok(chatMessageService.findAllMessage());
    }

    // Get messages between two users
    @GetMapping("/conversation")
    public ResponseEntity<List<ChatMessage>> getConversation(
    ) {
        return ResponseEntity.ok(
                chatMessageService.findAllMessage()
        );
    }

    // Get chat room messages
    @PostMapping("/add-message")
    public ResponseEntity<?> getRoomMessages(
            @RequestBody ChatMessage data) {
        chatMessageService.addMessage(data);
        return ResponseEntity.ok("Success"
        );
    }
}
