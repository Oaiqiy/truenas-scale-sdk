package dev.oaiqiy.truenas.scale.sdk.service.http.chart.release;

import dev.oaiqiy.truenas.scale.sdk.common.TrueNasCommand;
import dev.oaiqiy.truenas.scale.sdk.model.dto.ChartReleaseDetailDto;
import dev.oaiqiy.truenas.scale.sdk.service.TrueNasService;
import dev.oaiqiy.truenas.scale.sdk.service.http.AbstractHttpTrueNasService;

import java.util.Collections;
import java.util.Map;

@TrueNasService(TrueNasCommand.CHART_RELEASE)
public class TrueNasChartReleaseService extends AbstractHttpTrueNasService {
    @Override
    public Map<String, Object> execute(String... args) {
        String id = checkParam(args, 0);
        Map<String, String> params = Collections.singletonMap("id", id);
        ChartReleaseDetailDto chartReleaseDetail = getList(params, ChartReleaseDetailDto.class).stream()
                .findAny()
                .orElse(null);
        Map<String, Object> result = success();
        result.put("data", chartReleaseDetail);
        return result;
    }

    @Override
    protected String path() {
        return "/chart/release";
    }
}
