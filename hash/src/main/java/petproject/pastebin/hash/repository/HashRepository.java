package petproject.pastebin.hash.repository;

import io.lettuce.core.dynamic.annotation.Param;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import petproject.pastebin.hash.model.Hash;

public interface HashRepository extends JpaRepository<Hash, Long> {
    Hash findByValue(String hash);
    Long countByValue(String hash);
    Long countByIsBusy(boolean isBusy);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Hash getFirstByIsBusyFalse();
    @Modifying
    @Query("UPDATE Hash h SET h.isBusy = true WHERE h.isBusy = false AND h.id = :id")
    int markAsBusy(@Param("id") Long id);
}
