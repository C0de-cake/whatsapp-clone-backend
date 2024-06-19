package fr.codecake.whatsappclone.infrastructure.secondary.repository;

import fr.codecake.whatsappclone.infrastructure.secondary.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMessageRepository extends JpaRepository<MessageEntity, Long> {
}
