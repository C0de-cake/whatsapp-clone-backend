package fr.codecake.whatsappclone.infrastructure.secondary.repository;

import fr.codecake.whatsappclone.infrastructure.secondary.entity.ConversationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaConversationRepository extends JpaRepository<ConversationEntity, Long> {
}
