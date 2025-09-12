package com.binary.easychatsystem.service.impl;

import com.binary.easychatsystem.model.*;
import com.binary.easychatsystem.repository.ChatConversationParticipantRepository;
import com.binary.easychatsystem.repository.ChatMessageRepository;
import com.binary.easychatsystem.repository.UserRepository;
import com.binary.easychatsystem.service.ChatMessageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatConversationParticipantRepository participantRepository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public List<ChatMessage> findAllMessage() {
        return chatMessageRepository.findAll();
    }

    @Override
    public ChatMessage findMessageByID(Long id) {
        Optional<ChatMessage> opt = chatMessageRepository.findById(id);
        return opt.orElse(null);
    }

    @Override
    public void addMessage(ChatMessage data) {
        chatMessageRepository.save(data);
    }

    @Override
    public void deleteAll() {
        chatMessageRepository.deleteAll();
    }

    @Override
    public void deleteById(Long id) {
        chatMessageRepository.deleteById(id);
    }


    @Transactional
    public ChatConversation createDirectConversation(Long userAId, Long userBId, Long createdById) {
        User userA = userRepository.findById(userAId).orElseThrow();
        User userB = userRepository.findById(userBId).orElseThrow();
        User creator = userRepository.findById(createdById).orElseThrow();

        ChatConversation conv = new ChatConversation();
        conv.setType(ChatConversation.ConversationType.DIRECT);
        conv.setCreatedBy(creator);
     //   conv = conversationRepository.save(conv);

        addParticipant(conv.getConversationId(), userA.getUserId());
        addParticipant(conv.getConversationId(), userB.getUserId());
        return conv;
    }

    @Transactional
    public ChatConversation createGroupConversation(String title, Long creatorId, List<Long> memberIds) {
        User creator = userRepository.findById(creatorId).orElseThrow();

        ChatConversation conv = new ChatConversation();
        conv.setType(ChatConversation.ConversationType.GROUP);
        conv.setTitle(title);
        conv.setCreatedBy(creator);
      //  conv = conversationRepo.save(conv);

        addParticipant(conv.getConversationId(), creatorId);
        for (Long uid : memberIds) {
            if (!uid.equals(creatorId)) addParticipant(conv.getConversationId(), uid);
        }
        return conv;
    }

    @Transactional
    public void addParticipant(Long conversationId, Long userId) {
//        ChatConversation conv = participantRepository.findById(conversationId).orElseThrow();
//        User user = userRepository.findById(userId).orElseThrow();

//        if (!participantRepository.existsByConversationAndUser(conv, user)) {
//            ChatConversationParticipant participant = new ChatConversationParticipant();
//            ChatConversationParticipantId id = new ChatConversationParticipantId(conversationId, userId);
//            participant.setId(id);
//            participant.setConversation(conv);
//            participant.setUser(user);
//            participantRepo.save(participant);
//        }
    }
}
