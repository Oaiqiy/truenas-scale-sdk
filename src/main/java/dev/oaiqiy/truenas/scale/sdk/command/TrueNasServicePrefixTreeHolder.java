package dev.oaiqiy.truenas.scale.sdk.command;

import dev.oaiqiy.truenas.scale.sdk.service.ITrueNasService;

import java.util.Map;

public class TrueNasServicePrefixTreeHolder {

    private static final TrueNasServicePrefixTree root = new TrueNasServicePrefixTree();

    public synchronized static void init(Map<String, ITrueNasService> commands) {
        commands.forEach((key, value) -> root.add(key.trim().split("\\s+"), value));
    }

    public synchronized static void add(String c, ITrueNasService command) {
        root.add(c.trim().split("\\s+"), command);
    }

    public static Map<String, Object> execute(String s) {
        return root.execute(s.trim().split("\\s+"));
    }
}
