package petproject.pastebin.hash.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import petproject.pastebin.hash.model.Hash;
import petproject.pastebin.hash.repository.HashRepository;

@Service
public class HashServiceImpl implements HashService {

    @Autowired
    private HashRepository hashRepository;

    @Override
    @Transactional
    public String take() {
        Hash hash = hashRepository.getFirstByIsBusyFalse();
        if (hash != null && hashRepository.markAsBusy(hash.getId()) > 0) {
            return hash.getValue();
        } else {
            throw new RuntimeException("No available Hash.");
        }
    }

    @Override
    public boolean free(String value) {
        Hash hash = hashRepository.findByValue(value);
        hash.setIsBusy(false);
        hashRepository.save(hash);
        return true;
    }
}
