package racingcar.entity;

import java.util.Objects;

public class PlayersInfo {
    private final Integer id;
    private final String name;
    private final Integer position;
    private final Integer playResultId;

    public PlayersInfo(Integer id, String name, Integer position, Integer playResultId) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.playResultId = playResultId;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPosition() {
        return position;
    }

    public Integer getPlayResultId() {
        return playResultId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayersInfo that = (PlayersInfo) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(position, that.position) && Objects.equals(playResultId, that.playResultId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, position, playResultId);
    }

    @Override
    public String toString() {
        return "PlayersInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", position=" + position +
                ", playResultId=" + playResultId +
                '}';
    }
}
