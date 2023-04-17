package racingcar.dto;

import racingcar.domain.Car;

public class CarDto {

    private final String name;
    private final int position;

    public CarDto(String name, int position) {
        this.name = name;
        this.position = position;
    }

    public CarDto(Car car){
        this(car.getName(),car.getPosition());
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }
}
