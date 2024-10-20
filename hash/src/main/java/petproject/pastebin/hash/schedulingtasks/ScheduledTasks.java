package petproject.pastebin.hash.schedulingtasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import petproject.pastebin.hash.generation.ShortLinkGenerator;
import petproject.pastebin.hash.model.Hash;
import petproject.pastebin.hash.repository.HashRepository;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

@Component
public class ScheduledTasks {
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    @Autowired
    private HashRepository hashRepository;

    @Autowired
    ShortLinkGenerator shortLinkGenerator;

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        log.info("The time is now {}", dateFormat.format(new Date()));
    }

    @Scheduled(fixedRate = 1000)
    public void hashChecker() {
        Long countOfHashes = hashRepository.count();
        Long countOfBusyHashes = hashRepository.countByIsBusy(true);

        if(countOfHashes == 0) {
            for (int i = 1; i < 128; i++) {
                Hash hash = new Hash(Base64.getEncoder().encodeToString(Integer.toString(i).getBytes()), false);
                hashRepository.save(hash);
            }
        }

        if(countOfBusyHashes > hashRepository.count() * 0.5)  {
            for (long i = countOfHashes; i < countOfHashes * 1.5; i++) {
                Hash hash = new Hash(shortLinkGenerator.generateRandomLink(), false);
                hashRepository.save(hash);
            }
        }

        log.info("The count of all hashes {}", hashRepository.count());
        log.info("The count of busy hashes {}", hashRepository.countByIsBusy(true));
        log.info("The count of free hashes {}", hashRepository.countByIsBusy(false));
    }
}
