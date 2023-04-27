package racingcar;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RacingCarWebApplicationTests {

    @Autowired
    private RacingCarWebApplication racingCarWebApplication;

    @Test
    void contextLoads() {
        assertThat(racingCarWebApplication).isNotNull();
    }

}
