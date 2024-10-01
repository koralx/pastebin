package petproject.pastebin.api.service;

import petproject.pastebin.api.dto.BinDto;

public interface BinService {
    String create(BinDto dto);
    BinDto get(String hash);
    BinDto update(String hash, BinDto dto);
    void delete(String hash);
}
