package petproject.pastebin.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestClient;
import petproject.pastebin.api.dto.BinDto;
import petproject.pastebin.api.entity.Bin;
import petproject.pastebin.api.exception.BinNotFoundException;
import petproject.pastebin.api.repository.BinRepository;

import java.util.UUID;

@Service
public class YandexBinService implements BinService {

    @Autowired
    private YandexCloudService yandexCloudService;
    @Autowired
    private BinRepository pasteRepository;

    public String create(BinDto dto) {
        Bin paste = new Bin();

        paste.setTitle(dto.getTitle());
        paste.setTags(dto.getTags());
        paste.setKey(UUID.randomUUID().toString() + ".txt");

        RestClient restClient = RestClient.create();
        String result = restClient.get()
                .uri("http://localhost:8081/api/v1")
                .retrieve()
                .body(String.class);

        paste.setHashURL(result);

        yandexCloudService.upload(paste.getKey(), dto.getText());

        pasteRepository.save(paste);
        return "localhost:8080/api/v1/" + paste.getHashURL();
    }

    public BinDto get(String hash) {
        Bin paste = pasteRepository.findPasteByHashURL(hash);
        if (paste == null) {
            throw new BinNotFoundException(String.format("Bin with %s hash not found.", hash));
        }
        BinDto dto = new BinDto();

        dto.setTitle(paste.getTitle());
        dto.setTags(paste.getTags());
        dto.setText(yandexCloudService.download(paste.getKey()));

        return dto;
    }

    public BinDto update(String hash, BinDto dto) {

        Bin paste = pasteRepository.findPasteByHashURL(hash);

        if (paste == null) {
            throw new BinNotFoundException(String.format("Bin with %s hash not found.", hash));
        }

        if (!dto.getTitle().isEmpty()) {
            paste.setTitle(dto.getTitle());
        }

        if(!dto.getTags().isEmpty()) {
            paste.setTags(dto.getTags());
        }

        if(!dto.getText().isEmpty()) {
            yandexCloudService.upload(paste.getKey(), dto.getText());
        }

        pasteRepository.save(paste);

        return dto;
    }

    public void delete(String hash) {
        Bin paste = pasteRepository.findPasteByHashURL(hash);

        if (paste == null) {
            throw new BinNotFoundException(String.format("Bin with %s hash not found.", hash));
        }

        pasteRepository.delete(paste);
    }
}
