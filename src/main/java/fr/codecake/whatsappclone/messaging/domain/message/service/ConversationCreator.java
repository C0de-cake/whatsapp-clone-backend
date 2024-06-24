package fr.codecake.whatsappclone.messaging.domain.message.service;

import fr.codecake.whatsappclone.messaging.domain.message.aggregate.Conversation;
import fr.codecake.whatsappclone.messaging.domain.message.aggregate.ConversationToCreate;
import fr.codecake.whatsappclone.messaging.domain.message.repository.ConversationRepository;
import fr.codecake.whatsappclone.messaging.domain.user.aggregate.User;
import fr.codecake.whatsappclone.messaging.domain.user.service.UserReader;
import fr.codecake.whatsappclone.messaging.domain.user.vo.UserPublicId;
import fr.codecake.whatsappclone.shared.service.State;

import java.util.List;
import java.util.Optional;

public class ConversationCreator {

    private final ConversationRepository conversationRepository;
    private final UserReader userReader;

    public ConversationCreator(ConversationRepository conversationRepository, UserReader userReader) {
        this.conversationRepository = conversationRepository;
        this.userReader = userReader;
    }


    public State<Conversation, String> create(ConversationToCreate newConversation, User authenticatedUser) {
        newConversation.getMembers().add(authenticatedUser.getUserPublicId());
        List<User> members = userReader.getUsersByPublicId(newConversation.getMembers());
        List<UserPublicId> membersUuids = members.stream().map(User::getUserPublicId).toList();
        Optional<Conversation> conversationAlreadyPresent = conversationRepository.getConversationByUserPublicIds(membersUuids);
        State<Conversation, String> stateResult;
        if (conversationAlreadyPresent.isEmpty()) {
            Conversation newConversationSaved = conversationRepository.save(newConversation, members);
            stateResult = State.<Conversation, String>builder().forSuccess(newConversationSaved);
        } else {
            stateResult = State.<Conversation, String>builder().forError("This conversation already exists");
        }
        return stateResult;
    }
}
