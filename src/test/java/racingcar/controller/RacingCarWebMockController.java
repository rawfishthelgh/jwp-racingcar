package racingcar.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import racingcar.dto.GameInfoForRequest;
import racingcar.service.RacingCarService;

import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class RacingCarWebMockController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RacingCarService racingCarService;

    private static ObjectMapper objectMapper;

    @BeforeAll
    static void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void createCarsAndGameRecordsTest() throws Exception {
        GameInfoForRequest gameInfoForRequest = new GameInfoForRequest("마드,푸우", 10);
        String json = objectMapper.writeValueAsString(gameInfoForRequest);

        mockMvc.perform(post("/plays")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        verify(racingCarService, times(1)).createResponse(refEq(gameInfoForRequest));
    }

    @Test
    void showPlayRecordsTest() throws Exception {

        mockMvc.perform(get("/plays")).andExpect(status().isOk());

        verify(racingCarService, times(1)).showPlayRecords();
    }
}
