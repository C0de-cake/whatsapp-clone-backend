package fr.codecake.whatsappclone.messaging.domain.message.repository;

import fr.codecake.whatsappclone.messaging.domain.message.aggregate.Conversation;
import fr.codecake.whatsappclone.messaging.domain.message.aggregate.ConversationToCreate;
import fr.codecake.whatsappclone.messaging.domain.message.vo.ConversationPublicId;
import fr.codecake.whatsappclone.messaging.domain.user.aggregate.User;
import fr.codecake.whatsappclone.messaging.domain.user.vo.UserPublicId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ConversationRepository {

    Conversation save(ConversationToCreate conversation, List<User> members);

    Optional<Conversation> get(ConversationPublicId conversationPublicId);

    Page<Conversation> getConversationByUserPublicId(UserPublicId publicId, Pageable pageable);

    int deleteByPublicId(UserPublicId userPublicId, ConversationPublicId conversationPublicId);

    Optional<Conversation> getConversationByUsersPublicIdAndPublicId(UserPublicId userPublicId, ConversationPublicId conversationPublicId);

    Optional<Conversation> getConversationByUserPublicIds(List<UserPublicId> publicIds);

    Optional<Conversation> getOneByPublicId(ConversationPublicId conversationPublicId);
}
