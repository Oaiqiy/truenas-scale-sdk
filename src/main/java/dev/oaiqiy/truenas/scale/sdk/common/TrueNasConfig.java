package dev.oaiqiy.truenas.scale.sdk.common;

public class TrueNasConfig {
    public static volatile String URL_PREFIX = System.getProperty("TRUE_NAS_URL_PREFIX");

    public static volatile String TOKEN = System.getProperty("TRUE_NAS_TOKEN");

    public static volatile int RETRY_TIMES = 600;

    public static volatile int RETRY_INTERVAL_SECOND = 1;

    static {
    }

}
