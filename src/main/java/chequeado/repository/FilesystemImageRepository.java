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

@Repository
@Qualifier("Filesystem")
public class FilesystemImageRepository implements ImageRepository {

    public static class Entry {
        @JsonProperty("file_id")
        private String fileId;

        public void setFileId(String fileId) {
            this.fileId = fileId;
        }

        public String getFileId() {
            return fileId;
        }
    }


    public static final String REPO_FILE = "/images/images.json";

    private static final MyLogger logger = MyLogger.logger(FilesystemImageRepository.class);
    private final MemoryImageRepo memoryRepo;
    private final ObjectMapper mapper;

    public FilesystemImageRepository(MemoryImageRepo memoryRepo) throws IOException {
        this.memoryRepo = memoryRepo;
        this.mapper = new ObjectMapper();

        List<Entry> entries = loadEntries();
        entries.stream().map(Entry::getFileId).forEach(memoryRepo::put);
        logger.info("Loaded {} entries from {}", entries.size(), REPO_FILE);
    }

    private List<Entry> loadEntries() throws IOException {
        File repoFile = new File(REPO_FILE);
        if (!repoFile.exists()) {
            return new ArrayList<>();
        }

        FileReader fileReader = new FileReader(repoFile);
        List<Entry> entries = mapper.readValue(fileReader, new TypeReference<List<Entry>>() {});
        fileReader.close();
        return entries;
    }

    @Override
    synchronized public boolean put(String fileId) {
        memoryRepo.put(fileId);
        try {
            List<Entry> entries = loadEntries();
            Entry entry = new Entry();
            entry.setFileId(fileId);
            entries.add(entry);
            writeEntries(entries);
            return true;
        } catch (IOException e) {
            logger.error("Something went wrong while adding an entry: {}", e.getMessage());
        }
        return false;
    }

    private void writeEntries(List<Entry> entries) throws IOException {
        File file = new File(REPO_FILE);
        if (!file.exists()) {
            file.createNewFile();
        }
        mapper.writeValue(file, entries);
    }

    @Override
    public boolean hasImages() {
        return memoryRepo.hasImages();
    }

    @Override
    public String getAny() {
        return memoryRepo.getAny();
    }
}
