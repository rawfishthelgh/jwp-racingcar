package racingcar.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import racingcar.dto.CarForSave;
import racingcar.entity.PlayersInfo;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PlayersInfoDaoTest {

    private PlayersInfoDao playersInfoDao;
    private PlayResultDao playResultDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<CarForSave> carForSaveRowMapper = (rs, rowNum) -> new CarForSave(
            rs.getString("name"),
            rs.getInt("position")
    );

    @BeforeEach
    void setDao() {
        playersInfoDao = new PlayersInfoDao(jdbcTemplate);
        playResultDao = new PlayResultDao(jdbcTemplate);
    }

    @Test
    void insertTest() {
        //given
        List<CarForSave> carForSaves = List.of(new CarForSave("마드", 10), new CarForSave("푸우", 9));
        int playResultId = playResultDao.returnPlayResultIdAfterInsert(10, "마드");

        //when
        playersInfoDao.insert(playResultId, carForSaves);
        String sql = "select * from players_info";
        List<CarForSave> returnedCarForSaves = jdbcTemplate.query(sql, carForSaveRowMapper);

        //then
        assertThat(returnedCarForSaves.get(0)).isEqualTo(new CarForSave("마드", 10));
        assertThat(returnedCarForSaves.get(1)).isEqualTo(new CarForSave("푸우", 9));
    }

    @Test
    void findPlayersInfosByPlayResultIdTest() {
        //given
        List<CarForSave> carForSavesId1 = List.of(new CarForSave("마드", 10), new CarForSave("푸우", 9));
        List<CarForSave> carForSavesId2 = List.of(new CarForSave("민트", 10), new CarForSave("하디", 9));

        //when
        int playResultId1 = playResultDao.returnPlayResultIdAfterInsert(10, "마드");
        int playResultId2 = playResultDao.returnPlayResultIdAfterInsert(10, "민트");

        playersInfoDao.insert(playResultId1, carForSavesId1);
        playersInfoDao.insert(playResultId2, carForSavesId2);

        List<PlayersInfo> playersInfosId1 = playersInfoDao.findPlayersInfosByPlayResultId(playResultId1);
        List<PlayersInfo> playersInfosId2 = playersInfoDao.findPlayersInfosByPlayResultId(playResultId2);

        //then
        assertThat(playersInfosId1.get(0)).isEqualTo(new PlayersInfo(1, "마드", 10, playResultId1));
        assertThat(playersInfosId1.get(1)).isEqualTo(new PlayersInfo(2, "푸우", 9, playResultId1));

        assertThat(playersInfosId2.get(0)).isEqualTo(new PlayersInfo(3, "민트", 10, playResultId2));
        assertThat(playersInfosId2.get(1)).isEqualTo(new PlayersInfo(4, "하디", 9, playResultId2));


    }
}