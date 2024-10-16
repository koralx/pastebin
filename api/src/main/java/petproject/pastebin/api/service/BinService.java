package petproject.pastebin.api.service;

import org.springframework.stereotype.Service;
import petproject.pastebin.api.dto.BinDto;

@Service
public interface BinService {
    String create(BinDto dto);
    BinDto get(String hash);
    BinDto update(String hash, BinDto dto);
    void delete(String hash);
}
