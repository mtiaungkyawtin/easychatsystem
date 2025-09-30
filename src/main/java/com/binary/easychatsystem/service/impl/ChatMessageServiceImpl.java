package com.binary.easychatsystem.service.impl;

import com.binary.easychatsystem.dto.SendMessageRequest;
import com.binary.easychatsystem.model.*;
import com.binary.easychatsystem.repository.ChatConversationParticipantRepository;
import com.binary.easychatsystem.repository.ChatConversationRepository;
import com.binary.easychatsystem.repository.ChatMessageRepository;
import com.binary.easychatsystem.repository.UserRepository;
import com.binary.easychatsystem.service.ChatMessageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatMessageServiceImpl implements ChatMessageService {
    private static final Logger logger = LoggerFactory.getLogger(ChatMessageServiceImpl.class);
    private final ChatMessageRepository chatMessageRepository;
    private final ChatConversationRepository conversationRepository;
    private final ChatConversationParticipantRepository participantRepository;
    private final UserRepository userRepository;

    @Override
    public List<ChatMessage> findAllMessage() {
        return List.of();
    }

    @Override
    public ChatMessage findMessageByID(Long id) {
        return null;
    }

    @Override
    public void addMessage(ChatMessage data) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public ChatMessage sendMessage(SendMessageRequest request) {
        ChatConversation conversation = conversationRepository.findById(request.getConversationId())
                .orElseThrow(() -> new RuntimeException("Conversation not found"));

        // Verify sender is participant
        if (!participantRepository.existsByConversationIdAndUserId(request.getConversationId(), request.getSenderId())) {
            throw new RuntimeException("User is not a participant in this conversation");
        }

        ChatMessage message = ChatMessage.builder()
                .content(request.getContent())
                .mediaType(ChatMessage.MediaType.TEXT) // Determine from request
                .mediaUrl(request.getMediaUrl())
                .mediaMime(request.getMediaMime())
                .mediaSize(request.getMediaSize())
                .senderId(String.valueOf(request.getSenderId()))
                .recipientId(request.getRecipientId() != null ? String.valueOf(request.getRecipientId()) : null)
                .type(ChatMessage.MessageType.CHAT)
                .status(ChatMessage.MessageStatus.SENT)
                .conversation(conversation)
                .build();
        logger.info("Saving message: {}", message.toString());
        return chatMessageRepository.save(message);
    }

    @Override
    public List<ChatMessage> getConversationMessages(Long conversationId, Long userId, int page, int size) {
        // Verify user has access to conversation
        if (!participantRepository.existsByConversationIdAndUserId(conversationId, userId)) {
            throw new RuntimeException("Access denied");
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        var res =chatMessageRepository.findByConversation_ConversationIdOrderByCreatedAtDesc(conversationId, pageable);
        logger.info("return result: {}", res.toString());
        return res;
    }

    @Override
    public void markMessagesAsRead(Long conversationId, Long userId) {
        chatMessageRepository.markMessagesAsRead(conversationId, String.valueOf(userId));
    }
}
