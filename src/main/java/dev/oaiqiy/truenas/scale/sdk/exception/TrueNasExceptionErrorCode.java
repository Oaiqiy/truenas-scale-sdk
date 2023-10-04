package dev.oaiqiy.truenas.scale.sdk.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TrueNasExceptionErrorCode {
    HTTP_FAILED(1, "True nas api request exception, please retry"),
    ILLEGAL_PARAM(2, "Illegal param, please reenter"),
    NO_SUCH_SERVICE(3, "No such service, please implement"),
    NO_SUCH_DATASET(4),
    DATASET_NOT_ENCRYPTED(5),
    RETRY_TIME_OUT(6),
    NO_SUCH_CHART_RELEASE(7),
    ;

    private final int code;

    private String message;

    TrueNasExceptionErrorCode(int code) {
        this.code = code;
        message = name();
    }
}
