package racingcar.controller;


import racingcar.domain.CarsFactory;
import racingcar.domain.Cars;
import racingcar.genertor.NumberGenerator;
import racingcar.service.GamePlay;
import racingcar.view.InputView;
import racingcar.view.OutputView;

import static racingcar.view.OutputView.printResultNotice;
import static racingcar.view.OutputView.printWinner;

public class RacingCarConsoleController {

    private final NumberGenerator numberGenerator;

    public void startGame() {
        try {
            OutputView.printInputCarNamesNotice();
            Cars cars = CarsFactory.buildCars(InputView.inputCarNames());
            OutputView.printInputTryTimesNotice();
            int tryTimes = InputView.inputTryTimes();
            GamePlay.play(cars, tryTimes, numberGenerator);
            printWinner(cars.findWinners());
            printResultNotice();
            OutputView.printCarNameAndPosition(cars);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public RacingCarConsoleController(NumberGenerator numberGenerator) {
        this.numberGenerator = numberGenerator;
    }

}
