package petproject.pastebin.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import petproject.pastebin.api.entity.Bin;

public interface BinRepository extends JpaRepository<Bin, Long> {
    Bin findPasteByKey(String key);
    Bin findPasteByHashURL(String hashURL);
}
