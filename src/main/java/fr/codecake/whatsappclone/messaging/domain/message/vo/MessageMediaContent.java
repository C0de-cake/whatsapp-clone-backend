package fr.codecake.whatsappclone.messaging.domain.message.vo;

public record MessageMediaContent(byte[] file,
                                  String mimetype) {
}
