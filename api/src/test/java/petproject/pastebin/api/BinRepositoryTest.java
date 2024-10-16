package petproject.pastebin.api;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import petproject.pastebin.api.entity.Bin;
import petproject.pastebin.api.repository.BinRepository;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BinRepositoryTest {
    @Autowired
    private BinRepository underTest;

    @Test
    public void itShouldCheckIfBinExistsByHash() {
        //given
        Bin bin = new Bin();
        bin.setTitle("test");
        bin.setTags("test");
        String key = UUID.randomUUID().toString() + ".txt";
        bin.setKey(key);
        underTest.save(bin);

        //when
        boolean expected = underTest.existsBinByKey(key);

        //then
        assertThat(expected).isTrue();
    }

}
