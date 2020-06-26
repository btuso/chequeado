package chequeado.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class SendSticker {

    @JsonProperty("chat_id")
    private String chatId;
    private String sticker;
    @JsonProperty("disable_notification")
    private Boolean disableNotification;
    @JsonProperty("reply_to_message_id")
    private Long replyToMessageId;

    public SendSticker(String chatId, String sticker) {
        this.chatId = chatId;
        this.sticker = sticker;
    }

    public void setDisableNotification(Boolean disableNotification) {
        this.disableNotification = disableNotification;
    }

    public void setReplyToMessageId(Long replyToMessageId) {
        this.replyToMessageId = replyToMessageId;
    }

    public String getChatId() {
        return chatId;
    }

    public String getSticker() {
        return sticker;
    }

    public Boolean getDisableNotification() {
        return disableNotification;
    }

    public Long getReplyToMessageId() {
        return replyToMessageId;
    }
}
