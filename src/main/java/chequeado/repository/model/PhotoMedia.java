package chequeado.repository.model;

import chequeado.repository.model.Media;
import chequeado.repository.model.MediaType;

public class PhotoMedia extends Media {

    public PhotoMedia(String fileId) {
        super(MediaType.PHOTO, fileId);
    }

}
