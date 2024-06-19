package fr.codecake.whatsappclone.messaging.domain.message.vo;

import fr.codecake.whatsappclone.shared.error.domain.Assert;

import java.time.Instant;

public record MessageSentTime(Instant date) {
    public MessageSentTime {
        Assert.field("date", date).notNull();
    }
}
