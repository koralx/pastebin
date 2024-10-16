package petproject.pastebin.hash.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisCounterService {

    private static final String COUNTER_KEY = "counter";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public Long incrementCounterAndGet() {
        return stringRedisTemplate.opsForValue().increment(COUNTER_KEY);
    }

}