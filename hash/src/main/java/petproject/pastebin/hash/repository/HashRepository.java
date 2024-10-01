package petproject.pastebin.hash.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import petproject.pastebin.hash.model.Hash;

public interface HashRepository extends JpaRepository<Hash, Long> {
}
