package com.binary.easychatsystem.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter @NoArgsConstructor @AllArgsConstructor
public class ReadReceiptRequest {
    private Long conversationId;
    private Long userId;
    private List<Long> messageIds;
}
