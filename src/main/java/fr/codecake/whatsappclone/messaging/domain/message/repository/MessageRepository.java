package fr.codecake.whatsappclone.messaging.domain.message.repository;

import fr.codecake.whatsappclone.messaging.domain.message.aggregate.Conversation;
import fr.codecake.whatsappclone.messaging.domain.message.aggregate.Message;
import fr.codecake.whatsappclone.messaging.domain.message.vo.ConversationPublicId;
import fr.codecake.whatsappclone.messaging.domain.message.vo.MessageSendState;
import fr.codecake.whatsappclone.messaging.domain.user.aggregate.User;
import fr.codecake.whatsappclone.messaging.domain.user.vo.UserPublicId;

import java.util.List;

public interface MessageRepository {

    Message save(Message message, User sender, Conversation conversation);

    int updateMessageSendState(ConversationPublicId conversationPublicId, UserPublicId userPublicId, MessageSendState state);

    List<Message> findMessageToUpdateSendState(ConversationPublicId conversationPublicId, UserPublicId userPublicId);

}
