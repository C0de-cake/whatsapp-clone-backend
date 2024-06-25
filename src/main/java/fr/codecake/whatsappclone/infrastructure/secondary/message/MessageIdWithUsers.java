package fr.codecake.whatsappclone.infrastructure.secondary.message;

import fr.codecake.whatsappclone.messaging.domain.user.vo.UserPublicId;

import java.util.List;

public record MessageIdWithUsers(ConversationViewedForNotification conversationViewedForNotification,
                                 List<UserPublicId> usersToNotify) {
}
