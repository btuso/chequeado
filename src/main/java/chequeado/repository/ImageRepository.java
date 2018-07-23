package chequeado.repository;

public interface ImageRepository {

    boolean put(String fileId);

    boolean hasImages();

    String getAny();
}
