package petproject.pastebin.hash.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping()
    public String getHash() {
        Hash hash = new Hash();
        hash.setValue(hashGenerationService.getHash());
        hashRepository.save(hash);
        System.out.println(hash);
        return hash.getValue();
    }
}
