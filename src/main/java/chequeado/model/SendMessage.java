package chequeado.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class SendMessage {

    @JsonProperty("chat_id")
    private String chatId;
    private String text;
    @JsonProperty("disable_notification")
    private Boolean disableNotification;

    public SendMessage(String chatId, String text) {
        this.chatId = chatId;
        this.text = text;
    }

    public void setDisableNotification(Boolean disableNotification) {
        this.disableNotification = disableNotification;
    }

    public String getChatId() {
        return chatId;
    }

    public String getText() {
        return text;
    }

    public Boolean getDisableNotification() {
        return disableNotification;
    }

}
