package fr.codecake.whatsappclone.messaging.domain.message.aggregate;

import fr.codecake.whatsappclone.messaging.domain.message.vo.ConversationPublicId;
import fr.codecake.whatsappclone.messaging.domain.message.vo.MessageContent;

public record MessageSendNew(MessageContent messageContent,
                             ConversationPublicId conversationPublicId) {
}
