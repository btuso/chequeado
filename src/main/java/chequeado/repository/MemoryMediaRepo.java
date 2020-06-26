package chequeado.repository;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import chequeado.repository.model.Media;

@Repository
public class MemoryMediaRepo implements MediaRepository {

    private List<Media> media = new ArrayList<>();

    @Override
    public boolean put(Media mediaToAdd) {
        media.add(mediaToAdd);
        return true;
    }

    @Override
    public boolean hasMedia() {
        return !media.isEmpty();
    }

    @Override
    public Media getAny() {
        int index = new Random().nextInt(media.size());
        return media.get(index);
    }

}
