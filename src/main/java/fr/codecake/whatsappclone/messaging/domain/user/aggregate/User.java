package fr.codecake.whatsappclone.messaging.domain.user.aggregate;

import fr.codecake.whatsappclone.messaging.domain.user.vo.*;
import fr.codecake.whatsappclone.shared.error.domain.Assert;
import org.jilt.Builder;

import java.time.Instant;
import java.util.Set;

@Builder
public class User {

    private UserLastName lastName;

    private UserFirstname firstname;

    private UserEmail email;

    private UserPublicId userPublicId;

    private UserImageUrl imageUrl;

    private Instant lastModifiedDate;

    private Instant createdDate;

    private Instant lastSeen;

    private Set<Authority> authorities;

    private Long dbId;

    public User(UserLastName lastName, UserFirstname firstname, UserEmail email,
                UserPublicId userPublicId, UserImageUrl imageUrl, Instant lastModifiedDate,
                Instant createdDate, Instant lastSeen, Set<Authority> authorities, Long dbId) {
        assertMandatoryFields(lastName, firstname, email, authorities);
        this.lastName = lastName;
        this.firstname = firstname;
        this.email = email;
        this.userPublicId = userPublicId;
        this.imageUrl = imageUrl;
        this.lastModifiedDate = lastModifiedDate;
        this.createdDate = createdDate;
        this.lastSeen = lastSeen;
        this.authorities = authorities;
        this.dbId = dbId;
    }

    private void assertMandatoryFields(UserLastName lastName,
                                       UserFirstname firstname,
                                       UserEmail email,
                                       Set<Authority> authorities) {
        Assert.notNull("lastName", lastName);
        Assert.notNull("firstname", firstname);
        Assert.notNull("email", email);
        Assert.notNull("authorities", authorities);
    }

    public void updateFromUser(User user) {
        this.email = user.email;
        this.lastName = user.lastName;
        this.firstname = user.firstname;
        this.imageUrl = user.imageUrl;
    }

    public void initFieldForSignup() {
        this.lastSeen = Instant.now();
    }

    public UserLastName getLastName() {
        return lastName;
    }

    public UserFirstname getFirstname() {
        return firstname;
    }

    public UserEmail getEmail() {
        return email;
    }

    public UserPublicId getUserPublicId() {
        return userPublicId;
    }

    public UserImageUrl getImageUrl() {
        return imageUrl;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Instant getLastSeen() {
        return lastSeen;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public Long getDbId() {
        return dbId;
    }
}

