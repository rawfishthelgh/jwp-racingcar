package racingcar.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import racingcar.dao.PlayResultDao;
import racingcar.dao.PlayersInfoDao;
import racingcar.domain.CarsFactory;
import racingcar.domain.Cars;
import racingcar.dto.*;
import racingcar.genertor.NumberGenerator;
import racingcar.genertor.RandomNumberGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RacingCarService {

    private static final String DELIMITER = ",";

    private final PlayResultDao playResultDao;
    private final PlayersInfoDao playersInfoDao;
    private final GamePlay gamePlay;

    public RacingCarService(PlayResultDao playResultDao, PlayersInfoDao playersInfoDao, GamePlay gamePlay) {
        this.playResultDao = playResultDao;
        this.playersInfoDao = playersInfoDao;
        this.gamePlay = gamePlay;
    }

    public GameResultResponse createResponse(GameInfoRequest gameInfoRequest) {
        List<String> carNames = Arrays.asList(gameInfoRequest.getNames().split(","));
        Cars cars = CarsFactory.buildCars(carNames);
        NumberGenerator numberGenerator = new RandomNumberGenerator();
        int count = gameInfoRequest.getCount();
        gamePlay.play(cars, count, numberGenerator);
        List<CarResponse> carResponses = cars.getCars().stream()
                .map(CarResponse::new)
                .collect(Collectors.toList());
        List<CarResponse> winners = cars.findWinners().stream()
                .map(CarResponse::new)
                .collect(Collectors.toList());
        saveResult(count, carResponses, winners);
        return new GameResultResponse(cars.findWinners(), cars.getCars());
    }

    private void saveResult(int trialCount, List<CarResponse> cars, List<CarResponse> winners) {
        int playerResultId = playResultDao.returnPlayResultIdAfterInsert(trialCount, makeWinnersString(winners));
        playersInfoDao.insert(playerResultId, cars);
    }

    private String makeWinnersString(List<CarResponse> winners) {
        return winners.stream()
                .map(CarResponse::getName)
                .collect(Collectors.joining(DELIMITER));
    }

    public List<PlayRecordsResponse> showPlayRecords() {
        List<String> allWinners = playResultDao.findAllPlayRecords();
        List<PlayRecordsResponse> playRecordsResponses = new ArrayList<>();
        for (int i = 0; i < allWinners.size(); i++) {
            String winners = allWinners.get(i);
            int gameId = i + 1;
            PlayRecordsResponse playRecordsResponse = new PlayRecordsResponse(winners, playResultDao.findPlayRecordsByWinner(winners, gameId));
            playRecordsResponses.add(playRecordsResponse);
        }
        return playRecordsResponses;
    }
}
