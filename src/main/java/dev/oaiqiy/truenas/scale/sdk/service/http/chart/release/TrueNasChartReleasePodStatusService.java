package dev.oaiqiy.truenas.scale.sdk.service.http.chart.release;

import dev.oaiqiy.truenas.scale.sdk.common.TrueNasCommand;
import dev.oaiqiy.truenas.scale.sdk.service.TrueNasService;
import dev.oaiqiy.truenas.scale.sdk.service.http.AbstractHttpTrueNasService;

import java.util.Map;

@TrueNasService(TrueNasCommand.CHART_RELEASE_POD_STATUS)
public class TrueNasChartReleasePodStatusService extends AbstractHttpTrueNasService {
    @Override
    public Map<String, Object> execute(String... args) {
        String releaseName = checkParam(args, 0);
        Map response = post(releaseName);
        Map<String, Object> result = success();
        result.put("data", response);
        return result;
    }

    @Override
    protected String path() {
        return "/chart/release/pod_status";
    }
}
