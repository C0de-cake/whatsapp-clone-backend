package fr.codecake.whatsappclone.messaging.domain.user.aggregate;

import fr.codecake.whatsappclone.messaging.domain.user.vo.AuthorityName;
import fr.codecake.whatsappclone.shared.error.domain.Assert;
import org.jilt.Builder;

@Builder
public class Authority {

    private AuthorityName name;

    public Authority(AuthorityName name) {
        Assert.notNull("name", name);
        this.name = name;
    }

    public AuthorityName getName() {
        return name;
    }
}
