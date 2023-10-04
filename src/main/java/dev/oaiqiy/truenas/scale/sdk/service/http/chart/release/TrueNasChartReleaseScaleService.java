package dev.oaiqiy.truenas.scale.sdk.service.http.chart.release;

import com.alibaba.fastjson2.JSONObject;
import dev.oaiqiy.truenas.scale.sdk.TrueNasClient;
import dev.oaiqiy.truenas.scale.sdk.common.TrueNasCommand;
import dev.oaiqiy.truenas.scale.sdk.exception.TrueNasExceptionErrorCode;
import dev.oaiqiy.truenas.scale.sdk.service.TrueNasService;
import dev.oaiqiy.truenas.scale.sdk.service.http.AbstractHttpTrueNasService;

import java.util.Map;
import java.util.Objects;

import static dev.oaiqiy.truenas.scale.sdk.exception.TrueNasException.exception;

@TrueNasService(TrueNasCommand.CHART_RELEASE_SCALE)
public class TrueNasChartReleaseScaleService extends AbstractHttpTrueNasService {
    @Override
    public Map<String, Object> execute(String... args) {
        String releaseName = checkParam(args, 0);
        Long replicaCount = checkParam(args, 1, Long.class);


        Map<String, Object> result = TrueNasClient.execute(TrueNasCommand.CHART_RELEASE, releaseName);
        if (Objects.isNull(result.get("data"))) {
            throw exception(TrueNasExceptionErrorCode.NO_SUCH_CHART_RELEASE);
        }

        JSONObject body = new JSONObject();
        body.put("release_name", releaseName);
        JSONObject scaleOptions = body.putObject("scale_options");
        scaleOptions.put("replica_count", replicaCount);

        post(body, String.class);

        return success();
    }

    @Override
    protected String path() {
        return "/chart/release/scale";
    }
}
