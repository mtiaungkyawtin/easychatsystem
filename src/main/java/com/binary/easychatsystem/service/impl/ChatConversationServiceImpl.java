package com.binary.easychatsystem.service.impl;

import com.binary.easychatsystem.dto.ConversationDTO;
import com.binary.easychatsystem.dto.CreateConversationRequest;
import com.binary.easychatsystem.model.ChatConversation;
import com.binary.easychatsystem.model.ChatConversationParticipant;
import com.binary.easychatsystem.repository.ChatConversationParticipantRepository;
import com.binary.easychatsystem.repository.ChatConversationRepository;
import com.binary.easychatsystem.repository.UserRepository;
import com.binary.easychatsystem.service.ChatConversationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatConversationServiceImpl implements ChatConversationService {
    private final ChatConversationRepository conversationRepository;
    private final ChatConversationParticipantRepository participantRepository;
    private final UserRepository userRepository;

    @Override
    public List<ConversationDTO> getUserConversations(Long userId) {
        List<ChatConversation> conversations = conversationRepository.findUserConversations(userId);
        return conversations.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public ConversationDTO createConversation(CreateConversationRequest request, Long creatorId) {
        ChatConversation conversation = new ChatConversation();
        conversation.setType(request.getType());
        conversation.setCreatedBy(userRepository.findById(creatorId).orElseThrow());

        if (request.getType() == ChatConversation.ConversationType.GROUP) {
            conversation.setTitle(request.getTitle());
        } else {
            conversation.setTitle(null); // DIRECT conversations don't have titles
        }

        conversation = conversationRepository.save(conversation);

        // Add participants
        if (request.getType() == ChatConversation.ConversationType.DIRECT) {
            addParticipant(conversation.getConversationId(), creatorId);
            addParticipant(conversation.getConversationId(), request.getRecipientId());
        } else {
            // GROUP conversation
            addParticipant(conversation.getConversationId(), creatorId);
            for (Long participantId : request.getParticipantIds()) {
                if (!participantId.equals(creatorId)) {
                    addParticipant(conversation.getConversationId(), participantId);
                }
            }
        }

        return convertToDTO(conversation);
    }

    private void addParticipant(Long conversationId, Long userId) {
        if (!participantRepository.existsByConversationIdAndUserId(conversationId, userId)) {
            ChatConversationParticipant participant = new ChatConversationParticipant();
            participant.setConversationId(conversationId);
            participant.setUserId(userId);
            participantRepository.save(participant);
        }
    }

    private ConversationDTO convertToDTO(ChatConversation conversation) {
        ConversationDTO dto = new ConversationDTO();
        dto.setConversationId(conversation.getConversationId());
        dto.setType(conversation.getType());
        dto.setTitle(conversation.getTitle());
        dto.setCreatedAt(conversation.getCreatedAt());
        // Add logic to get last message and unread count
        return dto;
    }
}
