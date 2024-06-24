package fr.codecake.whatsappclone.infrastructure.primary.user;

import fr.codecake.whatsappclone.messaging.domain.user.aggregate.User;
import org.jilt.Builder;

import java.util.UUID;

@Builder
public record RestSearchUser(String lastName,
                             String firstName,
                             String email,
                             UUID publicId,
                             String imageUrl) {

    public static RestSearchUser from(User user) {
        RestSearchUserBuilder restSearchUserBuilder = RestSearchUserBuilder.restSearchUser();

        if (user.getLastName() != null) {
            restSearchUserBuilder.lastName(user.getLastName().value());
        }

        if (user.getFirstname() != null) {
            restSearchUserBuilder.firstName(user.getFirstname().value());
        }

        if (user.getImageUrl() != null) {
            restSearchUserBuilder.imageUrl(user.getImageUrl().value());
        }

        return restSearchUserBuilder.email(user.getEmail().value())
                .publicId(user.getUserPublicId().value())
                .build();
    }
}
