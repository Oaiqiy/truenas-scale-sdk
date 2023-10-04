package dev.oaiqiy.truenas.scale.sdk.service;

import java.util.Map;

public interface ITrueNasService {
    Map<String, Object> execute(String... args);

}
