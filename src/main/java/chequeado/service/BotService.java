package chequeado.service;

import chequeado.MyLogger;
import chequeado.connector.TelegramConnector;
import chequeado.model.Message;
import chequeado.model.PhotoSize;
import chequeado.model.SendMessage;
import chequeado.model.SendPhoto;
import chequeado.repository.ImageRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class BotService {

    private static final MyLogger logger = MyLogger.logger(BotService.class);

    private static final int PERSON_SHRUG_EMOJI = 129335;
    private static final int OK_HAND_EMOJI = 128076;
    private static final int THUMBS_DOWN_EMOJI = 128078;

    private static final String NO_REPLY_MESSAGE = "Que cosa?";
    private static final String EMPTY_REPO_MESSAGE = "No tengo imagenes jaja salu2";
    private static final String AWAITING_MEDIA_MESSAGE = "Esperando imagenes o stickers...";

    private final TelegramConnector connector;
    private final ImageRepository repository;

    boolean awaitingMedia;

    public BotService(TelegramConnector connector, @Qualifier("Filesystem") ImageRepository repository) {
        this.connector = connector;
        this.repository = repository;
        this.awaitingMedia = false;
    }

    public void noOp(Message message) {
        logger.debug("No operation for message {}", message.getMessageId());
    }

    public void echo(Message message) {
        SendMessage sendMessage;
        try {
            ObjectMapper mapper = new ObjectMapper();
            sendMessage = createReply(message, "Message: " + mapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            sendMessage = createReply(message, "Error reading that thingy");
        }
        connector.sendMessage(sendMessage);
    }

    public void addMedia(Message message) {
        if (!awaitingMedia) {
            return;
        }

        if (message.getSticker() != null) {
            addSticker(message);
        } else {
            addPhoto(message);
        }
    }

    public void addSticker(Message message) {
        Sticker sticker = message.getSticker();
        if (sticker == null) {
            connector.sendMessage(createReply(message, createEmoji(PERSON_SHRUG_EMOJI)));
            return;
        }

        logger.info("Adding sticker with file id: " + sticker.getFileId());
        boolean persisted = repository.put(sticker.getFileId());
        sendConfirmationMessage(persisted);
    }

    public void addPhoto(Message message) {
        List<PhotoSize> photos = message.getPhoto();
        if (CollectionUtils.isEmpty(photos)) {
            connector.sendMessage(createReply(message, createEmoji(PERSON_SHRUG_EMOJI)));
            return;
        }

        PhotoSize biggest = photos.get(photos.size() - 1);
        boolean persisted = repository.put(biggest.getFileId());
        sendConfirmationMessage(persisted);
    }

    private void sendConfirmationMessage(Message message, boolean success) {
        int emoji = success ? OK_HAND_EMOJI : THUMBS_DOWN_EMOJI;
        connector.sendMessage(createReply(message, createEmoji(emoji)));
    }

    private SendMessage createReply(Message message, String text) {
        Long id = message.getChat().getId();
        SendMessage sendMessage = new SendMessage(String.valueOf(id), text);
        sendMessage.setDisableNotification(true);
        return sendMessage;
    }

    private String createEmoji(Integer code) {
        char[] emoji = Character.toChars(code);
        return new String(emoji);
    }

    public void check(Message message) {
        if (message.getReplyToMessage() == null) {
            connector.sendMessage(createReply(message, NO_REPLY_MESSAGE));
            return;
        }
        if (!repository.hasImages()) {
            connector.sendMessage(createReply(message, EMPTY_REPO_MESSAGE));
            return;
        }

        String checkImage = repository.getAny();
        connector.sendImage(createPhotoReply(message, checkImage));
    }

    private SendPhoto createPhotoReply(Message message, String fileId) {
        Long id = message.getChat().getId();
        SendPhoto sendPhoto = new SendPhoto(String.valueOf(id), fileId);
        sendPhoto.setDisableNotification(true);
        sendPhoto.setReplyToMessageId(message.getReplyToMessage().getMessageId());
        return sendPhoto;
    }

    public void awaitNewMedia(Message message) {
        this.awaitingMedia = true;
        connector.sendMessage(createReply(message, AWAITING_MEDIA_MESSAGE));
    }

    public void stopAwaitingMedia(Message message) {
        this.awaitingMedia = false;
        sendConfirmationMessage(message, true);
    }

}
