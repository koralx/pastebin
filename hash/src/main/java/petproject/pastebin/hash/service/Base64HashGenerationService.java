package petproject.pastebin.hash.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class Base64HashGenerationService implements HashGenerationService {

    @Autowired
    RedisCounterService redisCounterService;

    @Override
    public String getHash() {
        redisCounterService.incrementCounter();
        return Base64.getEncoder().encodeToString(redisCounterService.getCounterValue().toString().getBytes());
    }
}
