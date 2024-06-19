package fr.codecake.whatsappclone.messaging.domain.user.vo;

import fr.codecake.whatsappclone.shared.error.domain.Assert;

public record UserLastName(String value) {

    public UserLastName {
        Assert.field(value, value).maxLength(255);
    }
}
