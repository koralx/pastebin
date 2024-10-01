package petproject.pastebin.hash.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisCounterService {

    private static final String COUNTER_KEY = "counter";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public Long incrementCounter() {
        return stringRedisTemplate.opsForValue().increment(COUNTER_KEY);
    }

    public Long getCounterValue() {
        String value = stringRedisTemplate.opsForValue().get(COUNTER_KEY);
        return value != null ? Long.parseLong(value) : 0L;
    }
}