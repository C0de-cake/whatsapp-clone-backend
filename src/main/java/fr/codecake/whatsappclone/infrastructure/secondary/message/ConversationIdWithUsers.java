package fr.codecake.whatsappclone.infrastructure.secondary.message;

import fr.codecake.whatsappclone.messaging.domain.message.vo.ConversationPublicId;
import fr.codecake.whatsappclone.messaging.domain.user.vo.UserPublicId;

import java.util.List;

public record ConversationIdWithUsers(ConversationPublicId conversationPublicId,
                                      List<UserPublicId> users) {
}
