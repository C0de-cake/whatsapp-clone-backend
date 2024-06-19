package fr.codecake.whatsappclone.infrastructure.secondary.repository;

import fr.codecake.whatsappclone.infrastructure.secondary.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {
}
