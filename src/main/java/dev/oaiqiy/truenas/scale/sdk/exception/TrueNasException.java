package dev.oaiqiy.truenas.scale.sdk.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TrueNasException extends RuntimeException {

    private int code;

    private TrueNasException(TrueNasExceptionErrorCode code) {
        super(code.getMessage());
        this.code = code.getCode();
    }

    public static TrueNasException exception(TrueNasExceptionErrorCode code) {
        return new TrueNasException(code);
    }
}
