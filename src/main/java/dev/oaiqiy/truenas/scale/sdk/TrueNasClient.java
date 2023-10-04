package dev.oaiqiy.truenas.scale.sdk;

import dev.oaiqiy.truenas.scale.sdk.common.TrueNasCommand;
import dev.oaiqiy.truenas.scale.sdk.service.ITrueNasService;
import dev.oaiqiy.truenas.scale.sdk.service.TrueNasService;
import org.reflections.Reflections;

import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static dev.oaiqiy.truenas.scale.sdk.exception.TrueNasException.exception;
import static dev.oaiqiy.truenas.scale.sdk.exception.TrueNasExceptionErrorCode.NO_SUCH_SERVICE;

public class TrueNasClient {
    private static final Map<TrueNasCommand, ITrueNasService> serviceMap;

    static {

        Reflections serviceReflections = new Reflections("dev.oaiqiy.truenas.scale.sdk.service");
        Set<Class<? extends ITrueNasService>> trueNasServices = serviceReflections.getSubTypesOf(ITrueNasService.class);
        serviceMap = trueNasServices.stream()
                .filter(c -> !c.isInterface())
                .filter(c -> !Modifier.isAbstract(c.getModifiers()))
                .filter(c -> c.getAnnotation(TrueNasService.class) != null)
                .collect(Collectors.toMap(c -> c.getAnnotation(TrueNasService.class).value(), c -> {
                    try {
                        return c.newInstance();
                    } catch (InstantiationException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }));

    }

    public static Map<String, Object> execute(String command, String... args) {
        return execute(TrueNasCommand.parse(command), args);
    }

    public static Map<String, Object> execute(TrueNasCommand command, String... args) {
        if (!serviceMap.containsKey(command)) {
            throw exception(NO_SUCH_SERVICE);
        }
        return serviceMap.get(command).execute(args);
    }

}
