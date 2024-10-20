package petproject.pastebin.hash.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import petproject.pastebin.hash.service.HashService;

@RestController
@RequestMapping("/api/v1")
public class HashController {

    @Autowired
    HashService hashService;

    @GetMapping()
    public String takeHash() {
        return hashService.take();
    }

    @PostMapping()
    public Boolean freeHash(String value) {
        return hashService.free(value);
    }
}
