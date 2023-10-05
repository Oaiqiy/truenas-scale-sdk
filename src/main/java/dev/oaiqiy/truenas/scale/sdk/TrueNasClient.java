package dev.oaiqiy.truenas.scale.sdk;

import com.alibaba.fastjson2.JSON;
import dev.oaiqiy.truenas.scale.sdk.command.TrueNasServicePrefixTreeHolder;
import dev.oaiqiy.truenas.scale.sdk.common.TrueNasCommand;
import dev.oaiqiy.truenas.scale.sdk.service.ITrueNasService;
import dev.oaiqiy.truenas.scale.sdk.service.TrueNasService;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
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

        Map<String, ITrueNasService> commandMap = new HashMap<>();
        serviceMap.forEach((k, v) -> {
            commandMap.put(k.getCommand(), v);
            if(StringUtils.isNotBlank(k.getAlias())){
                commandMap.put(k.getAlias(), v);
            }
        });
        TrueNasServicePrefixTreeHolder.init(commandMap);
    }

    public static Map<String, Object> execute(String command) {
        return TrueNasServicePrefixTreeHolder.execute(command);
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

    public static void main(String[] args) {
        if (args.length == 1) {
            System.out.println(JSON.toJSON(execute(args[0])));
        } else {
            Scanner in = new Scanner(System.in);
            String c = in.nextLine();
            System.out.println(JSON.toJSON(execute(c)));
        }
    }
}

