package chequeado.repository.model;

import chequeado.repository.model.Media;
import chequeado.repository.model.MediaType;

public class StickerMedia extends Media {

    public StickerMedia(String fileId) {
        super(MediaType.STICKER, fileId);
    }

}
