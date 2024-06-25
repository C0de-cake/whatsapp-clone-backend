package fr.codecake.whatsappclone.infrastructure.secondary.message;

import fr.codecake.whatsappclone.infrastructure.primary.message.RestMessage;
import fr.codecake.whatsappclone.messaging.domain.message.aggregate.Message;
import fr.codecake.whatsappclone.messaging.domain.message.service.MessageChangeNotifier;
import fr.codecake.whatsappclone.messaging.domain.message.vo.ConversationPublicId;
import fr.codecake.whatsappclone.messaging.domain.user.vo.UserPublicId;
import fr.codecake.whatsappclone.shared.service.State;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpringEventMessageChangeNotifier implements MessageChangeNotifier {

    private final NotificationService notificationService;
    private ApplicationEventPublisher applicationEventPublisher;

    public SpringEventMessageChangeNotifier(ApplicationEventPublisher applicationEventPublisher, NotificationService notificationService) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.notificationService = notificationService;
    }

    @Override
    public State<Void, String> send(Message message, List<UserPublicId> userToNotify) {
        MessageWithUsers messageWithUsers = new MessageWithUsers(message, userToNotify);
        applicationEventPublisher.publishEvent(messageWithUsers);
        return State.<Void, String>builder().forSuccess();
    }

    @Override
    public State<Void, String> delete(ConversationPublicId conversationPublicId,
                                      List<UserPublicId> userToNotify) {
        ConversationIdWithUsers conversationIdWithUsers = new ConversationIdWithUsers(conversationPublicId, userToNotify);
        applicationEventPublisher.publishEvent(conversationIdWithUsers);
        return State.<Void, String>builder().forSuccess();
    }

    @EventListener
    public void handleDeleteConversation(ConversationIdWithUsers conversationIdWithUsers) {
        notificationService.sendMessage(conversationIdWithUsers.conversationPublicId().value(),
                conversationIdWithUsers.users(), NotificationEventName.DELETE_CONVERSATION);
    }

    @EventListener
    public void handleNewMessage(MessageWithUsers messageWithUsers) {
        notificationService.sendMessage(RestMessage.from(messageWithUsers.message()),
                messageWithUsers.userToNotify(), NotificationEventName.NEW_MESSAGE);
    }
}
