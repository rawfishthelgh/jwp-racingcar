package racingcar.dto;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GameInfoForRequest {
    private static final int MIN_PLAYER_NAME = 1;
    private static final int MAX_PLAYER_NAME = 5;
    private static final int MAX_PLAYER_NUM = 10;

    private final String names;
    private final Integer count;

    public GameInfoForRequest(String names, Integer count) throws IllegalArgumentException {
        List<String> players = Arrays.stream(names.split(",")).collect(Collectors.toList());
        validateNullNames(names);
        validateNullcount(count);
        validatePlayerSize(players);
        validateNameSize(players);
        validateCountSize(count);
        this.names = names;
        this.count = count;
    }

    private static void validateCountSize(Integer count) {
        if (count < 1 || count > 20) {
            throw new IllegalArgumentException("최소 1회 이상 최대 20회 이하로 실행해야 합니다");
        }
    }

    private static void validateNameSize(List<String> players) {
        if (players.stream().anyMatch(player -> player.length() < MIN_PLAYER_NAME || player.length() > MAX_PLAYER_NAME)) {
            throw new IllegalArgumentException("플레이어의 이름 길이는 1에서 5 사이여야 합니다");
        }
    }

    private static void validatePlayerSize(List<String> players) {
        if (players.size() > MAX_PLAYER_NUM || players.size() < 1) {
            throw new IllegalArgumentException("플레이어 수는 1~10명으로 제한됩니다");
        }
    }

    private static void validateNullNames(String names) {
        if (names.isBlank()) {
            throw new IllegalArgumentException("값을 입력해야 합니다");
        }
    }

    private static void validateNullcount(Integer count) {
        if (count == null) {
            throw new IllegalArgumentException("값을 입력해야 합니다");
        }
    }

    public String getNames() {
        return names;
    }

    public int getCount() {
        return count;
    }
}
