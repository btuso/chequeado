package chequeado.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class Update {

    @JsonProperty("update_id")
    private Long updateId;
    private Message message;

    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Long getUpdateId() {
        return updateId;
    }

    public Message getMessage() {
        return message;
    }

}
