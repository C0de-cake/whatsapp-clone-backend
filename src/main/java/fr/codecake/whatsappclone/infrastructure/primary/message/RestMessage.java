package fr.codecake.whatsappclone.infrastructure.primary.message;

import fr.codecake.whatsappclone.messaging.domain.message.aggregate.Message;
import fr.codecake.whatsappclone.messaging.domain.message.aggregate.MessageSendNew;
import fr.codecake.whatsappclone.messaging.domain.message.aggregate.MessageSendNewBuilder;
import fr.codecake.whatsappclone.messaging.domain.message.vo.*;
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

        if (message.getContent().type() != MessageType.TEXT) {
            restMessageBuilder.mediaContent(message.getContent().media().file())
                    .mimeType(message.getContent().media().mimetype());
        }

        return restMessageBuilder.build();
    }

    public static List<RestMessage> from(Set<Message> messages) {
        return messages.stream().map(RestMessage::from).toList();
    }

    public static MessageSendNew toDomain(RestMessage restMessage) {
        MessageContentBuilder messageContent = MessageContentBuilder.messageContent()
                .type(restMessage.type)
                .text(restMessage.textContent);

        if (!restMessage.type.equals(MessageType.TEXT)) {
            messageContent.media(new MessageMediaContent(restMessage.mediaContent,
                    restMessage.mimeType));
        }
        return MessageSendNewBuilder.messageSendNew()
                .messageContent(messageContent.build())
                .conversationPublicId(new ConversationPublicId(restMessage.conversationId))
                .build();
    }

    public boolean hasMedia() {
        return !type.equals(MessageType.TEXT);
    }

    public void setMediaAttachment(byte[] file, String contentType) {
        this.mediaContent = file;
        this.mimeType = contentType;
    }

    public String getTextContent() {
        return textContent;
    }

    public Instant getSendDate() {
        return sendDate;
    }

    public MessageSendState getState() {
        return state;
    }

    public UUID getPublicId() {
        return publicId;
    }

    public UUID getConversationId() {
        return conversationId;
    }

    public MessageType getType() {
        return type;
    }

    public byte[] getMediaContent() {
        return mediaContent;
    }

    public String getMimeType() {
        return mimeType;
    }

    public UUID getSenderId() {
        return senderId;
    }
}
