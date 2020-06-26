package chequeado.repository;

import chequeado.MyLogger;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import chequeado.repository.model.Media;

@Repository
@Qualifier("Filesystem")
public class FilesystemMediaRepository implements MediaRepository {

    public static final String REPO_FILE = "/images/media.json";

    private static final MyLogger logger = MyLogger.logger(FilesystemMediaRepository.class);
    private final MemoryMediaRepo memoryRepo;
    private final ObjectMapper mapper;

    public FilesystemMediaRepository(MemoryMediaRepo memoryRepo) throws IOException {
        this.memoryRepo = memoryRepo;
        this.mapper = new ObjectMapper();

        List<Media> entries = loadEntries();
        entries.forEach(memoryRepo::put);
        logger.info("Loaded {} entries from {}", entries.size(), REPO_FILE);
    }

    private List<Media> loadEntries() throws IOException {
        File repoFile = new File(REPO_FILE);
        if (!repoFile.exists()) {
            return new ArrayList<>();
        }

        FileReader fileReader = new FileReader(repoFile);
        List<Media> entries = mapper.readValue(fileReader, new TypeReference<List<Media>>() {});
        fileReader.close();
        return entries;
    }

    @Override
    synchronized public boolean put(Media media) {
        memoryRepo.put(media);
        try {
            List<Media> entries = loadEntries();
            entries.add(media);
            writeEntries(entries);
            return true;
        } catch (IOException e) {
            logger.error("Something went wrong while adding an entry: {}", e.getMessage());
        }
        return false;
    }

    private void writeEntries(List<Media> entries) throws IOException {
        File file = new File(REPO_FILE);
        if (!file.exists()) {
            file.createNewFile();
        }
        mapper.writeValue(file, entries);
    }

    @Override
    public boolean hasMedia() {
        return memoryRepo.hasMedia();
    }

    @Override
    public Media getAny() {
        return memoryRepo.getAny();
    }
}
