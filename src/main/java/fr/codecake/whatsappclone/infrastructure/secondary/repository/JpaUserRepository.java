package fr.codecake.whatsappclone.infrastructure.secondary.repository;

import fr.codecake.whatsappclone.infrastructure.secondary.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    @Query("SELECT user FROM UserEntity user WHERE lower(user.lastName) LIKE lower(concat('%', :query, '%')) " +
            "OR lower(user.firstName) LIKE lower(concat('%', :query, '%'))")
    Page<UserEntity> search(Pageable pageable, String query);

    List<UserEntity> findByPublicIdIn(List<UUID> publicIds);

    @Modifying
    @Query("UPDATE UserEntity  user SET user.lastSeen = :lastSeen WHERE user.publicId = :userPublicID")
    int updateLastSeen(UUID userPublicID, Instant lastSeen);

    Optional<UserEntity> findOneByPublicId(UUID publicId);

    List<UserEntity> findByConversationsPublicIdAndPublicIdIsNot(UUID conversationsPublicId, UUID publicIdToExclude);
}
