package fr.codecake.whatsappclone.infrastructure.primary.message;

import fr.codecake.whatsappclone.messaging.domain.message.aggregate.Message;
import fr.codecake.whatsappclone.messaging.domain.message.vo.MessageSendState;
import fr.codecake.whatsappclone.messaging.domain.message.vo.MessageType;
import org.jilt.Builder;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Builder
public class RestMessage {

    private String textContent;
    private Instant sendDate;
    private MessageSendState state;
    private UUID publicId;
    private UUID conversationId;
    private MessageType type;
    private byte[] mediaContent;
    private String mimeType;
    private UUID senderId;

    public RestMessage() {
    }

    public RestMessage(String textContent, Instant sendDate, MessageSendState state, UUID publicId, UUID conversationId,
                       MessageType type, byte[] mediaContent, String mimeType, UUID senderId) {
        this.textContent = textContent;
        this.sendDate = sendDate;
        this.state = state;
        this.publicId = publicId;
        this.conversationId = conversationId;
        this.type = type;
        this.mediaContent = mediaContent;
        this.mimeType = mimeType;
        this.senderId = senderId;
    }

    public static RestMessage from(Message message) {
        RestMessageBuilder restMessageBuilder = RestMessageBuilder.restMessage()
                .textContent(message.getContent().text())
                .sendDate(message.getSentTime().date())
                .state(message.getSendState())
                .publicId(message.getPublicId().value())
                .conversationId(message.getConversationId().value())
                .type(message.getContent().type())
                .senderId(message.getSender().value());

        if(message.getContent().type() != MessageType.TEXT) {
            restMessageBuilder.mediaContent(message.getContent().media().file())
                    .mimeType(message.getContent().media().mimetype());
        }

        return restMessageBuilder.build();
    }

    public static List<RestMessage> from(Set<Message> messages) {
        return messages.stream().map(RestMessage::from).toList();
    }
}
