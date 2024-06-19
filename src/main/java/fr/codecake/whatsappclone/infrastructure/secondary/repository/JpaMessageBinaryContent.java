package fr.codecake.whatsappclone.infrastructure.secondary.repository;

import fr.codecake.whatsappclone.infrastructure.secondary.entity.MessageContentBinaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMessageBinaryContent extends JpaRepository<MessageContentBinaryEntity, Long> {
}
