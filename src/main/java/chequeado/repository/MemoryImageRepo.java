package chequeado.repository;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Repository
public class MemoryImageRepo implements ImageRepository {

    private List<String> images = new ArrayList<>();

    @Override
    public boolean put(String fileId) {
        images.add(fileId);
        return true;
    }

    @Override
    public boolean hasImages() {
        return !images.isEmpty();
    }

    @Override
    public String getAny() {
        int index = new Random().nextInt(images.size());
        return images.get(index);
    }

}
