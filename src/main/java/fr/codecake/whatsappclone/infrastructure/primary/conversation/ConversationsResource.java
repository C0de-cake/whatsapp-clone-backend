package fr.codecake.whatsappclone.infrastructure.primary.conversation;

import fr.codecake.whatsappclone.messaging.application.ConversationsApplicationService;
import fr.codecake.whatsappclone.messaging.domain.message.aggregate.Conversation;
import fr.codecake.whatsappclone.messaging.domain.message.aggregate.ConversationToCreate;
import fr.codecake.whatsappclone.messaging.domain.message.vo.ConversationPublicId;
import fr.codecake.whatsappclone.shared.service.State;
import fr.codecake.whatsappclone.shared.service.StatusNotification;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/conversations")
public class ConversationsResource {

    private final ConversationsApplicationService conversationsApplicationService;

    public ConversationsResource(ConversationsApplicationService conversationsApplicationService) {
        this.conversationsApplicationService = conversationsApplicationService;
    }

    @GetMapping
    ResponseEntity<List<RestConversation>> getAll(Pageable pageable) {
        List<RestConversation> restConversations = conversationsApplicationService.getAllByConnectedUser(pageable)
                .stream().map(RestConversation::from).toList();
        return ResponseEntity.ok(restConversations);
    }

    @PostMapping
    ResponseEntity<RestConversation> create(@RequestBody
                                            RestConversationToCreate restConversationToCreate) {
        ConversationToCreate newConversation = RestConversationToCreate.toDomain(restConversationToCreate);
        State<Conversation, String> conversationState = conversationsApplicationService.create(newConversation);
        if (conversationState.getStatus().equals(StatusNotification.OK)) {
            RestConversation restConversations = RestConversation.from(conversationState.getValue());
            return ResponseEntity.ok(restConversations);
        } else {
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Not allowed to create conversation");
            return ResponseEntity.of(problemDetail).build();
        }
    }

    @DeleteMapping
    ResponseEntity<UUID> delete(@RequestParam UUID publicId) {
        State<ConversationPublicId, String> restConversation = conversationsApplicationService.delete(new ConversationPublicId(publicId));
        if (restConversation.getStatus().equals(StatusNotification.OK)) {
            return ResponseEntity.ok(publicId);
        } else {
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Not allowed to delete conversation");
            return ResponseEntity.of(problemDetail).build();
        }
    }

    @GetMapping("/get-one-by-public-id")
    ResponseEntity<RestConversation> getOneByPublicId(@RequestParam UUID conversationId) {
        Optional<RestConversation> restConversation = conversationsApplicationService.getOneByConversationId(new ConversationPublicId(conversationId))
                .map(RestConversation::from);
        if (restConversation.isPresent()) {
            return ResponseEntity.ok(restConversation.get());
        } else {
            ProblemDetail problemDetail = ProblemDetail
                    .forStatusAndDetail(HttpStatus.BAD_REQUEST, "Not able to find this conversation");
            return ResponseEntity.of(problemDetail).build();
        }
    }

    @PostMapping("/mark-as-read")
    ResponseEntity<Integer> markConversationAsRead(@RequestParam UUID conversationId) {
        State<Integer, String> readUpdateState = conversationsApplicationService.markConversationAsRead(
                new ConversationPublicId(conversationId));
        return ResponseEntity.ok(readUpdateState.getValue());
    }
}
