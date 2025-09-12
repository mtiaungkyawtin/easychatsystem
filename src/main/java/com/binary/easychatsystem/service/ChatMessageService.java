package com.binary.easychatsystem.service;

import com.binary.easychatsystem.model.ChatMessage;

import java.util.List;

public interface ChatMessageService {
    List<ChatMessage> findAllMessage();
    ChatMessage findMessageByID(Long id);
    void addMessage(ChatMessage data);
    void deleteAll();
    void deleteById(Long id);
}
