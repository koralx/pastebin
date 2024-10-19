package petproject.pastebin.hash.controller;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import petproject.pastebin.hash.model.Hash;
import petproject.pastebin.hash.repository.HashRepository;
import petproject.pastebin.hash.service.Base64HashGenerationService;

@RestController
@RequestMapping("/api/v1")
public class HashController {

    @Autowired
    private Base64HashGenerationService hashGenerationService;
    @Autowired
    private HashRepository hashRepository;

    @Transactional
    @GetMapping()
    public String getHash() {
        Hash hash = hashRepository.getFirstByIsBusyFalse();
        if (hash != null && hashRepository.markAsBusy(hash.getId()) > 0) {
            return hash.getValue();
        } else {
            throw new RuntimeException("No available Hash.");
        }
    }

    @PostMapping()
    public Boolean freeHash(String hashValue) {
        Hash hash = hashRepository.findByValue(hashValue);
        hash.setIsBusy(false);
        hashRepository.save(hash);
        return true;
    }
}
