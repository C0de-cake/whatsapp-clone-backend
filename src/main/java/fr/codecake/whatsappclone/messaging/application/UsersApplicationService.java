package fr.codecake.whatsappclone.messaging.application;

import fr.codecake.whatsappclone.messaging.domain.user.aggregate.User;
import fr.codecake.whatsappclone.messaging.domain.user.repository.UserRepository;
import fr.codecake.whatsappclone.messaging.domain.user.service.UserReader;
import fr.codecake.whatsappclone.messaging.domain.user.service.UserSynchronizer;
import fr.codecake.whatsappclone.messaging.domain.user.vo.UserEmail;
import fr.codecake.whatsappclone.shared.authentication.application.AuthenticatedUser;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsersApplicationService {

    private final UserSynchronizer userSynchronizer;
    private final UserReader userReader;

    public UsersApplicationService(UserRepository userRepository) {
        this.userSynchronizer = new UserSynchronizer(userRepository);
        this.userReader = new UserReader(userRepository);
    }

    @Transactional
    public User getAuthenticatedUserWithSync(Jwt oauth2User, boolean forceResync) {
        userSynchronizer.syncWithIdp(oauth2User, forceResync);
        return userReader.getByEmail(new UserEmail(AuthenticatedUser.username().get()))
                .orElseThrow();
    }

    @Transactional(readOnly = true)
    public User getAuthenticatedUser() {
        return userReader.getByEmail(new UserEmail(AuthenticatedUser.username().get()))
                .orElseThrow();
    }

    @Transactional(readOnly = true)
    public List<User> search(Pageable pageable, String query) {
        return userReader.search(pageable, query).stream().toList();
    }
}
