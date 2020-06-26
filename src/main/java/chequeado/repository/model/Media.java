package chequeado.repository.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import chequeado.repository.model.MediaType;

// TODO make this an abstract class
public class Media {

    @JsonProperty("media_type")
    private MediaType mediaType;
    @JsonProperty("file_id")
    private String fileId;


    public Media() {
        // For Jackson serialization
    }

    Media(MediaType mediaType, String fileId) {
        this.mediaType = mediaType;
        this.fileId = fileId;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }
}
