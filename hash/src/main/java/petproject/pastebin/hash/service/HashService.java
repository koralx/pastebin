package petproject.pastebin.hash.service;


public interface HashService {
    String take();
    boolean free(String hash);
}
