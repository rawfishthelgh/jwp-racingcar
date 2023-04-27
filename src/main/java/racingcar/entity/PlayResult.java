package racingcar.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class PlayResult {
    private final Integer id;
    private final Integer count;
    private final String winners;
    private final LocalDateTime createdAt;

    public PlayResult(Integer id, Integer count, String winners, LocalDateTime createdAt) {
        this.id = id;
        this.count = count;
        this.winners = winners;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public Integer getCount() {
        return count;
    }

    public String getWinners() {
        return winners;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayResult that = (PlayResult) o;
        return Objects.equals(id, that.id) && Objects.equals(count, that.count) && Objects.equals(winners, that.winners) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, count, winners, createdAt);
    }
}
