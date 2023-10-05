package dev.oaiqiy.truenas.scale.sdk.service.cli.system;

import dev.oaiqiy.truenas.scale.sdk.common.TrueNasCommand;
import dev.oaiqiy.truenas.scale.sdk.service.TrueNasService;
import dev.oaiqiy.truenas.scale.sdk.service.cli.AbstractCliTrueNasService;

import java.util.Map;

@TrueNasService(value = TrueNasCommand.SYSTEM_SHUTDOWN, remote = false)
public class TrueNasSystemShutdownService extends AbstractCliTrueNasService {
    @Override
    public Map<String, Object> execute(String... args) {
        run("shutdown");
        return success();
    }
}
