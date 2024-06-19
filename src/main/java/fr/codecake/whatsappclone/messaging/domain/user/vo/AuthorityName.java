package fr.codecake.whatsappclone.messaging.domain.user.vo;

import fr.codecake.whatsappclone.shared.error.domain.Assert;

public record AuthorityName(String name) {

    public AuthorityName {
        Assert.field("name", name).notNull();
    }
}
