package chequeado.repository;

import chequeado.repository.model.Media;

public interface MediaRepository {

    boolean put(Media media);

    boolean hasMedia();

    Media getAny();
}
