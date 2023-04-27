package racingcar.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import racingcar.entity.PlayResult;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PlayResultDaoTest {

    private PlayResultDao playResultDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setDao() {
        playResultDao = new PlayResultDao(jdbcTemplate);
    }

    private final RowMapper<PlayResult> playResultRowMapper = (resultSet, rowNum) ->
            new PlayResult(
                    resultSet.getInt("id"),
                    resultSet.getInt("count"),
                    resultSet.getString("winners"),
                    resultSet.getObject("created_at", LocalDateTime.class)
            );

    @Test
    void returnPlayResultIdAfterInsertTest() {
        //when
        int playResultId = playResultDao.returnPlayResultIdAfterInsert(10, "마드,푸우");
        String sql = "select * from play_result";
        List<PlayResult> playResults = jdbcTemplate.query(sql, playResultRowMapper);
        PlayResult playResult = playResults.get(0);

        //then
        assertThat(playResult.getId()).isEqualTo(1);
        assertThat(playResult.getCount()).isEqualTo(10);
        assertThat(playResult.getWinners()).isEqualTo("마드,푸우");
    }

    @Test
    void findAllPlayResultsTest() {
        //given
        int playResultId1 = playResultDao.returnPlayResultIdAfterInsert(10, "마드,푸우");
        int playResultId2 = playResultDao.returnPlayResultIdAfterInsert(9, "민트,하디");

        //when
        List<PlayResult> allPlayResults = playResultDao.findAllPlayResults();
        PlayResult playResult1 = allPlayResults.get(0);
        PlayResult playResult2 = allPlayResults.get(1);

        //then
        assertThat(playResult1.getId()).isEqualTo(playResultId1);
        assertThat(playResult1.getCount()).isEqualTo(10);
        assertThat(playResult1.getWinners()).isEqualTo("마드,푸우");

        assertThat(playResult2.getId()).isEqualTo(playResultId2);
        assertThat(playResult2.getCount()).isEqualTo(9);
        assertThat(playResult2.getWinners()).isEqualTo("민트,하디");
    }
}