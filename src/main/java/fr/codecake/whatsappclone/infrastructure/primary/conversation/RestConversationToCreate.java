package fr.codecake.whatsappclone.infrastructure.primary.conversation;

import fr.codecake.whatsappclone.messaging.domain.message.aggregate.ConversationToCreate;
import fr.codecake.whatsappclone.messaging.domain.message.aggregate.ConversationToCreateBuilder;
import fr.codecake.whatsappclone.messaging.domain.message.vo.ConversationName;
import fr.codecake.whatsappclone.messaging.domain.user.vo.UserPublicId;
import org.jilt.Builder;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Builder
public record RestConversationToCreate(Set<UUID> members, String name) {

    public static ConversationToCreate toDomain(RestConversationToCreate restConversationToCreate) {
        RestConversationToCreateBuilder restConversationToCreateBuilder = RestConversationToCreateBuilder.restConversationToCreate();

        Set<UserPublicId> userUUIDs = restConversationToCreate.members
                .stream()
                .map(UserPublicId::new)
                .collect(Collectors.toSet());

        return ConversationToCreateBuilder.conversationToCreate()
                .name(new ConversationName(restConversationToCreate.name()))
                .members(userUUIDs)
                .build();
    }
}
