package dev.oaiqiy.truenas.scale.sdk.service.http.chart.release;

import dev.oaiqiy.truenas.scale.sdk.common.TrueNasCommand;
import dev.oaiqiy.truenas.scale.sdk.model.dto.ChartReleaseDetailDto;
import dev.oaiqiy.truenas.scale.sdk.service.TrueNasService;
import dev.oaiqiy.truenas.scale.sdk.service.http.AbstractHttpTrueNasService;

import java.util.List;
import java.util.Map;

@TrueNasService(TrueNasCommand.CHART_RELEASE_LIST)
public class TrueNasChartReleaseListService extends AbstractHttpTrueNasService {
    @Override
    public Map<String, Object> execute(String... args) {
        List<ChartReleaseDetailDto> data = getList(null, ChartReleaseDetailDto.class);
        Map<String, Object> result = success();
        result.put("data", data);
        return result;
    }

    @Override
    protected String path() {
        return "/chart/release";
    }
}
