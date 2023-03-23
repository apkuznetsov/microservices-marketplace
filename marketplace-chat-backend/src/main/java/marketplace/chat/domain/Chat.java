package marketplace.chat.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import marketplace.chat.utils.RandomIdGenerator;

import java.io.Serializable;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Chat implements Serializable {

    @Builder.Default
    String id = RandomIdGenerator.generate();

    String name;

    @Builder.Default
    Long createdAt = Instant.now().toEpochMilli();

}
