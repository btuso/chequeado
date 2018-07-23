package chequeado.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class SendPhoto {

    @JsonProperty("chat_id")
    private String chatId;
    private String photo;
    @JsonProperty("disable_notification")
    private Boolean disableNotification;
    @JsonProperty("reply_to_message_id")
    private Long replyToMessageId;

    public SendPhoto(String chatId, String photo) {
        this.chatId = chatId;
        this.photo = photo;
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

    public String getPhoto() {
        return photo;
    }

    public Boolean getDisableNotification() {
        return disableNotification;
    }

    public Long getReplyToMessageId() {
        return replyToMessageId;
    }
}
