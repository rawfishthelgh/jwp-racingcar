package racingcar.dto;

import racingcar.domain.Car;

import java.util.Objects;

public class CarForSave {
    private final String name;
    private final int position;

    public CarForSave(String name, int position) {
        this.name = name;
        this.position = position;
    }

    public CarForSave(Car car) {
        this(car.getName(), car.getPosition());
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarForSave that = (CarForSave) o;
        return position == that.position && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, position);
    }
}
