package fr.codecake.whatsappclone.infrastructure.secondary.message;

import fr.codecake.whatsappclone.messaging.domain.message.aggregate.Message;
import fr.codecake.whatsappclone.messaging.domain.message.service.MessageChangeNotifier;
import fr.codecake.whatsappclone.messaging.domain.message.vo.ConversationPublicId;
import fr.codecake.whatsappclone.messaging.domain.user.vo.UserPublicId;
import fr.codecake.whatsappclone.shared.service.State;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpringEventMessageChangeNotifier implements MessageChangeNotifier {
    @Override
    public State<Void, String> send(Message message, List<UserPublicId> userToNotify) {
        return null;
    }

    @Override
    public State<Void, String> delete(ConversationPublicId conversationPublicId, List<UserPublicId> userToNotify) {
        return null;
    }
}
