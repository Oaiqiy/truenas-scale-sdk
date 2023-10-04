package dev.oaiqiy.truenas.scale.sdk.service.http.chart.release;

import dev.oaiqiy.truenas.scale.sdk.common.TrueNasCommand;
import dev.oaiqiy.truenas.scale.sdk.service.TrueNasService;

import java.util.Map;

@TrueNasService(TrueNasCommand.CHART_RELEASE_STOP)
public class TrueNasChartReleaseStopService extends TrueNasChartReleaseScaleService {
    @Override
    public Map<String, Object> execute(String... args) {
        String id = checkParam(args, 0);
        return super.execute(id, "0");
    }
}
