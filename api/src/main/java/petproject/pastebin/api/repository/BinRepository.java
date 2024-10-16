package petproject.pastebin.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import petproject.pastebin.api.entity.Bin;

@Repository
public interface BinRepository extends JpaRepository<Bin, Long> {
    Bin findPasteByKey(String key);
    Bin findPasteByHashURL(String hashURL);
    Boolean existsBinByKey(String key);
}
