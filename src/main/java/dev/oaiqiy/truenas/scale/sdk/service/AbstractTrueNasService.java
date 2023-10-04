package dev.oaiqiy.truenas.scale.sdk.service;

import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static dev.oaiqiy.truenas.scale.sdk.exception.TrueNasException.exception;
import static dev.oaiqiy.truenas.scale.sdk.exception.TrueNasExceptionErrorCode.ILLEGAL_PARAM;

public abstract class AbstractTrueNasService implements ITrueNasService {

    protected <T> T checkParam(String[] args, int idx, T defaultValue, Class<T> clazz) {
        try {
            String arg = args[idx];

            if (String.class.equals(clazz)) {
                return (T) arg;
            } else if (Long.class.equals(clazz)) {
                return (T) Long.valueOf(arg);
            } else if (Boolean.class.equals(clazz)) {
                return (T) Boolean.valueOf(arg);
            }
        } catch (Exception e) {
            if (Objects.nonNull(defaultValue)) {
                return defaultValue;
            }
            throw exception(ILLEGAL_PARAM);
        }
        throw exception(ILLEGAL_PARAM);
    }

    protected <T> T checkParam(String[] args, int idx, Class<T> clazz) {
        return checkParam(args, idx, null, clazz);
    }

    protected String checkParam(String[] args, int idx, String defaultValue) {
        return checkParam(args, idx, defaultValue, String.class);
    }

    protected String checkParam(String[] args, int idx) {
        return checkParam(args, idx, null, String.class);
    }

    protected Map<String, Object> success() {
        HashMap<String, Object> result = Maps.newHashMap();
        result.put("result", 1);
        return result;
    }
}
