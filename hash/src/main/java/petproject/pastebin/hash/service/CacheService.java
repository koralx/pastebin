package petproject.pastebin.hash.service;

public interface CacheService {
    void save(String key, Object value);
    Object get(String key);
    void delete(String key);
    boolean hasKey(String key);
}
