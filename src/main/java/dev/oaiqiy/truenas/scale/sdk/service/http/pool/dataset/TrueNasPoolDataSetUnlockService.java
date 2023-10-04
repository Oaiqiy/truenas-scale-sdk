package dev.oaiqiy.truenas.scale.sdk.service.http.pool.dataset;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import dev.oaiqiy.truenas.scale.sdk.TrueNasClient;
import dev.oaiqiy.truenas.scale.sdk.common.TrueNasCommand;
import dev.oaiqiy.truenas.scale.sdk.exception.TrueNasException;
import dev.oaiqiy.truenas.scale.sdk.exception.TrueNasExceptionErrorCode;
import dev.oaiqiy.truenas.scale.sdk.model.dto.PoolDatasetDetailDto;
import dev.oaiqiy.truenas.scale.sdk.service.TrueNasService;
import dev.oaiqiy.truenas.scale.sdk.service.http.AbstractHttpTrueNasService;

import java.util.List;
import java.util.Map;

import static dev.oaiqiy.truenas.scale.sdk.common.TrueNasConfig.RETRY_INTERVAL_SECOND;
import static dev.oaiqiy.truenas.scale.sdk.common.TrueNasConfig.RETRY_TIMES;

@TrueNasService(TrueNasCommand.POOL_DATASET_UNLOCK)
public class TrueNasPoolDataSetUnlockService extends AbstractHttpTrueNasService {


    @Override
    public Map<String, Object> execute(String... args) {

        String id = checkParam(args, 0);
        String passphrase = checkParam(args, 1);
        Boolean force = checkParam(args, 2, Boolean.FALSE, Boolean.class);

        PoolDatasetDetailDto datasetDto = ((List<PoolDatasetDetailDto>) TrueNasClient.execute(TrueNasCommand.POOL_DATASET, id)
                .get("data")).stream()
                .findAny()
                .orElseThrow(() -> TrueNasException.exception(TrueNasExceptionErrorCode.NO_SUCH_DATASET));
        if (!datasetDto.getEncrypted() || !datasetDto.getLocked()) {
            throw TrueNasException.exception(TrueNasExceptionErrorCode.DATASET_NOT_ENCRYPTED);
        }

        JSONObject body = new JSONObject();
        body.put("id", id);
        JSONObject unlockOptions = body.putObject("unlock_options");
        unlockOptions.put("force", force);
        unlockOptions.put("key_file", false);
        unlockOptions.put("recursive", false);
        unlockOptions.put("toggle_attachments", true);
        JSONArray datasets = unlockOptions.putArray("datasets");
        JSONObject dataset = datasets.addObject();
        dataset.put("force", force);
        dataset.put("name", id);
        dataset.put("passphrase", passphrase);

        post(body, String.class);

        for (int i = 0; i < RETRY_TIMES; i++) {
            datasetDto = ((List<PoolDatasetDetailDto>) TrueNasClient.execute(TrueNasCommand.POOL_DATASET, id)
                    .get("data")).stream()
                    .findAny()
                    .orElseThrow(() -> TrueNasException.exception(TrueNasExceptionErrorCode.NO_SUCH_DATASET));
            if (!datasetDto.getLocked()) {
                return success();
            }

            try {
                Thread.sleep(RETRY_INTERVAL_SECOND * 1000L);
            } catch (InterruptedException e) {
                throw TrueNasException.exception(TrueNasExceptionErrorCode.RETRY_TIME_OUT);
            }
        }

        throw TrueNasException.exception(TrueNasExceptionErrorCode.RETRY_TIME_OUT);

    }

    @Override
    protected String path() {
        return "/pool/dataset/unlock";
    }
}
