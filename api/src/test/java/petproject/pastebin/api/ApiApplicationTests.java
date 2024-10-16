package petproject.pastebin.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import petproject.pastebin.api.controller.BinController;

import petproject.pastebin.api.dto.BinDto;
import petproject.pastebin.api.service.S3BinService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BinController.class)
class ApiApplicationTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private S3BinService service;

    @Test
    void greetingShouldReturnMessageFromService() throws Exception {
        BinDto binDto = new BinDto();
        binDto.setTitle("title");
        binDto.setText("text");
        binDto.setTags("tags");
        this.mockMvc.perform(post("/api/v1").contentType(MediaType.APPLICATION_JSON).content(asJsonString(binDto))).andDo(print()).andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
