package petproject.pastebin.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import petproject.pastebin.api.dto.BinDto;
import petproject.pastebin.api.service.YandexBinService;

@RestController
@RequestMapping("/api/v1")
public class BinController {

    @Autowired
    private YandexBinService yandexService;

    @PostMapping()
    public String post(BinDto body) {
        return yandexService.create(body);
    }

    @GetMapping("/{key}")
    public BinDto get(@PathVariable String key) {
        return yandexService.get(key);
    }

    @PutMapping("/{key}")
    public BinDto update(@PathVariable String key, @RequestBody BinDto body) {
        return yandexService.update(key, body);
    }

    @DeleteMapping("/{key}")
    public void delete(@PathVariable String key) {yandexService.delete(key);}
}
