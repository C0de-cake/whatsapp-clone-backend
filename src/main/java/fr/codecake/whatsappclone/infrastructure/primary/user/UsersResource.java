package fr.codecake.whatsappclone.infrastructure.primary.user;

import fr.codecake.whatsappclone.messaging.application.UsersApplicationService;
import fr.codecake.whatsappclone.messaging.domain.user.aggregate.User;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersResource {

    private final UsersApplicationService usersApplicationService;

    public UsersResource(UsersApplicationService usersApplicationService) {
        this.usersApplicationService = usersApplicationService;
    }

    @GetMapping("/get-authenticated-user")
    public ResponseEntity<RestUser> getAuthenticatedUser(@AuthenticationPrincipal Jwt user,
                                                         @RequestParam boolean forceResync) {
        User authenticatedUser = usersApplicationService.getAuthenticatedUserWithSync(user, forceResync);
        RestUser restUser = RestUser.from(authenticatedUser);
        return ResponseEntity.ok(restUser);
    }

    @GetMapping("/search")
    public ResponseEntity<List<RestSearchUser>> search(Pageable pageable, @RequestParam String query) {
        List<RestSearchUser> searchResults = usersApplicationService.search(pageable, query)
                .stream().map(RestSearchUser::from)
                .toList();
        return ResponseEntity.ok(searchResults);
    }

}
