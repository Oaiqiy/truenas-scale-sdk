package dev.oaiqiy.truenas.scale.sdk.service.http.pool.dataset;

import com.google.common.collect.Maps;
import dev.oaiqiy.truenas.scale.sdk.common.TrueNasCommand;
import dev.oaiqiy.truenas.scale.sdk.model.dto.PoolDatasetDetailDto;
import dev.oaiqiy.truenas.scale.sdk.service.TrueNasService;
import dev.oaiqiy.truenas.scale.sdk.service.http.AbstractHttpTrueNasService;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@TrueNasService(TrueNasCommand.POOL_DATASET)
public class TrueNasPoolDatasetService extends AbstractHttpTrueNasService {

    @Override
    public Map<String, Object> execute(String... args) {
        String id = checkParam(args, 0, "");
        HashMap<String, String> params = Maps.newHashMap();
        if (StringUtils.isNotBlank(id)) {
            params.put("id", id);
        }
        List<PoolDatasetDetailDto> details = getList(params, PoolDatasetDetailDto.class);
        Map<String, Object> result = success();
        result.put("data", details);
        return result;
    }

    @Override
    protected String path() {
        return "/pool/dataset";
    }
}
