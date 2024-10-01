package petproject.pastebin.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import petproject.pastebin.api.controller.BinController;
import petproject.pastebin.api.dto.BinDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import com.fasterxml.jackson.databind.ObjectMapper;
import petproject.pastebin.api.service.YandexBinService;

@WebMvcTest(BinController.class)
public class SpringTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private YandexBinService mainService;
    @Test
    void test() throws Exception {
        BinDto pasteDTO = new BinDto();

        pasteDTO.setTitle("title");
        pasteDTO.setText("text");
        pasteDTO.setTags("tags");

        mvc.perform(post("/api/v1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(pasteDTO)))
                .andExpect(status().isOk());

    }
}
