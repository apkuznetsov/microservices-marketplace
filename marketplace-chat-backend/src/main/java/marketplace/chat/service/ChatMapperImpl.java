package marketplace.chat.service;

import marketplace.chat.domain.Chat;
import marketplace.chat.dto.ChatDto;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ChatMapperImpl {

    public ChatDto toChatDto(Chat chat) {
        return ChatDto.builder()
                .id(chat.getId())
                .name(chat.getName())
                .createdAt(Instant.ofEpochMilli(chat.getCreatedAt()))
                .build();
    }

}
