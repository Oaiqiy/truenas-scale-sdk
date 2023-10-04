package dev.oaiqiy.truenas.scale.sdk.service.combo.chart.release;

import dev.oaiqiy.truenas.scale.sdk.TrueNasClient;
import dev.oaiqiy.truenas.scale.sdk.common.TrueNasCommand;
import dev.oaiqiy.truenas.scale.sdk.exception.TrueNasExceptionErrorCode;
import dev.oaiqiy.truenas.scale.sdk.model.dto.ChartReleaseDetailDto;
import dev.oaiqiy.truenas.scale.sdk.service.AbstractTrueNasService;
import dev.oaiqiy.truenas.scale.sdk.service.TrueNasService;

import java.util.Map;

import static dev.oaiqiy.truenas.scale.sdk.common.TrueNasConfig.RETRY_INTERVAL_SECOND;
import static dev.oaiqiy.truenas.scale.sdk.common.TrueNasConfig.RETRY_TIMES;
import static dev.oaiqiy.truenas.scale.sdk.common.TrueNasConstant.CHART_RELEASE_STOPPED;
import static dev.oaiqiy.truenas.scale.sdk.exception.TrueNasException.exception;

@TrueNasService(TrueNasCommand.CHART_RELEASE_RESTART)
public class TrueNasChartReleaseRestartService extends AbstractTrueNasService {
    @Override
    public Map<String, Object> execute(String... args) {
        String id = checkParam(args, 0);
        TrueNasClient.execute(TrueNasCommand.CHART_RELEASE_STOP, id);

        for (int i = 0; i < RETRY_TIMES; i++) {
            ChartReleaseDetailDto detail =
                    (ChartReleaseDetailDto) TrueNasClient.execute(TrueNasCommand.CHART_RELEASE, id).get("data");
            if (CHART_RELEASE_STOPPED.equals(detail.getStatus())) {
                TrueNasClient.execute(TrueNasCommand.CHART_RELEASE_START, id);
                return success();
            }

            try {
                Thread.sleep(RETRY_INTERVAL_SECOND * 1000L);
            } catch (InterruptedException e) {
                throw exception(TrueNasExceptionErrorCode.RETRY_TIME_OUT);
            }
        }

        throw exception(TrueNasExceptionErrorCode.RETRY_TIME_OUT);
    }

}
