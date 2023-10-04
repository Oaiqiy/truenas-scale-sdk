package dev.oaiqiy.truenas.scale.sdk.service.http;

import dev.oaiqiy.truenas.scale.sdk.common.TrueNasHttpClient;
import dev.oaiqiy.truenas.scale.sdk.service.AbstractTrueNasService;

import java.util.List;
import java.util.Map;

public abstract class AbstractHttpTrueNasService extends AbstractTrueNasService {


    abstract protected String path();

    protected <T> T get(Map<String, String> params, Class<T> clazz) {
        return TrueNasHttpClient.get(path(), params, clazz);
    }

    protected <T> List<T> getList(Map<String, String> params, Class<T> clazz) {
        return TrueNasHttpClient.getList(path(), params, clazz);
    }

    protected <T> T post(Map<String, String> params, Object body, Class<T> targetClass) {
        return TrueNasHttpClient.post(path(), params, body, targetClass);
    }

    protected <T> T post(Object body, Class<T> targetClass) {
        return TrueNasHttpClient.post(path(), null, body, targetClass);
    }

    protected Map post(Object body) {
        return TrueNasHttpClient.post(path(), null, body, Map.class);
    }
}
