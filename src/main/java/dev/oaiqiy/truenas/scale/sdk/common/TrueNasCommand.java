package dev.oaiqiy.truenas.scale.sdk.common;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

import static dev.oaiqiy.truenas.scale.sdk.exception.TrueNasException.exception;
import static dev.oaiqiy.truenas.scale.sdk.exception.TrueNasExceptionErrorCode.NO_SUCH_SERVICE;

@AllArgsConstructor
@Getter
public enum TrueNasCommand {
    CHART_RELEASE("chart release", "app"),
    CHART_RELEASE_LIST("chart release list", "app list"),
    CHART_RELEASE_POD_STATUS("char release pod status", "app status"),
    CHART_RELEASE_SCALE("chart release scale", "app scale"),
    CHART_RELEASE_START("chart release start", "app start"),
    CHART_RELEASE_STOP("chart release stop", "app stop"),
    CHART_RELEASE_RESTART("chart release restart", "app restart"),

    POOL_DATASET_UNLOCK("pool dataset unlock", "unlock"),
    POOL_DATASET("pool dataset", null),
    ;

    private static final Map<String, TrueNasCommand> duplicate = Maps.newHashMap();

    static {
        Arrays.stream(values()).forEach(command -> {
            if (duplicate.containsKey(command.getCommand())) {
                throw new RuntimeException();
            }
            if (Objects.nonNull(command.getAlias()) && duplicate.containsKey(command.getAlias())) {
                throw new RuntimeException();
            }
            duplicate.put(command.getCommand(), command);
            duplicate.put(command.getAlias(), command);
        });
    }

    private final String command;

    private final String alias;

    public static TrueNasCommand parse(String s) {
        if (!duplicate.containsKey(s)) {
            throw exception(NO_SUCH_SERVICE);
        }
        return duplicate.get(s);
    }

}
