package petproject.pastebin.api.service;

import org.hibernate.NonUniqueResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;
import petproject.pastebin.api.dto.BinDto;
import petproject.pastebin.api.entity.Bin;
import petproject.pastebin.api.exception.BinNotFoundException;
import petproject.pastebin.api.exception.HashNoResponse;
import petproject.pastebin.api.repository.BinRepository;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class S3BinService implements BinService {

    @Autowired
    private S3CloudService yandexCloudService;
    @Autowired
    private BinRepository pasteRepository;

    public String create(BinDto dto) {
        Bin paste = new Bin();

        paste.setTitle(dto.getTitle());
        paste.setTags(dto.getTags());
        paste.setKey(UUID.randomUUID().toString() + ".txt");

        String result = getHashFromRemoteService();

        paste.setHashURL(result);

        yandexCloudService.upload(paste.getKey(), dto.getText());

        pasteRepository.save(paste);
        return "http://localhost:8080/api/v1/" + paste.getHashURL();
    }

    public String getHashFromRemoteService() {

        AtomicReference<String> result = new AtomicReference<>();

        RetryTemplate retry = RetryTemplate.builder()
                .maxAttempts(5)
                .fixedBackoff(1000)
                .retryOn(HashNoResponse.class)
                .build();

        return retry.execute(ctx -> {
            var restTemplateBuilder = new RestTemplateBuilder();
            RestTemplate rest = restTemplateBuilder.build();
            try {
                result.set(rest.getForObject("http://localhost:8081/api/v1", String.class));
            } catch (Exception e) {
                throw new HashNoResponse("Cant get hash from service.");
            }
            return result.get();
        });
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
