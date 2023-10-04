package dev.oaiqiy.truenas.scale.sdk.common;

import com.alibaba.fastjson2.JSON;
import org.apache.commons.collections4.MapUtils;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static dev.oaiqiy.truenas.scale.sdk.exception.TrueNasException.exception;
import static dev.oaiqiy.truenas.scale.sdk.exception.TrueNasExceptionErrorCode.HTTP_FAILED;

public class TrueNasHttpClient {
    private static final CloseableHttpClient client =
            HttpClientBuilder.create()
                    .setDefaultHeaders(createDefaultHeaders())
                    .build();


    public static <T> T get(String path, Map<String, String> params, Class<T> clazz) {
        String url = buildUrl(path, params);
        HttpGet httpGet = new HttpGet(url);
        try {
            CloseableHttpResponse response = client.execute(httpGet);
            return parseResponse(response, clazz);
        } catch (IOException e) {
            throw exception(HTTP_FAILED);
        }
    }

    public static <T> List<T> getList(String path, Map<String, String> params, Class<T> clazz) {
        String url = buildUrl(path, params);
        HttpGet httpGet = new HttpGet(url);
        try {
            CloseableHttpResponse response = client.execute(httpGet);
            return parseListResponse(response, clazz);
        } catch (IOException e) {
            throw exception(HTTP_FAILED);
        }
    }


    public static <T> T post(String path, Map<String, String> params, Object body, Class<T> clazz) {
        String url = buildUrl(path, params);
        HttpPost httpPost = new HttpPost(url);
        StringEntity stringEntity;
        if (body instanceof String) {
            stringEntity = new StringEntity((String) body, Charset.defaultCharset());
        } else {
            stringEntity = new StringEntity(JSON.toJSONString(body), Charset.defaultCharset());
        }
        stringEntity.setContentType(ContentType.APPLICATION_JSON.getMimeType());
        httpPost.setEntity(stringEntity);
        try {
            CloseableHttpResponse response = client.execute(httpPost);
            return parseResponse(response, clazz);
        } catch (IOException e) {
            throw exception(HTTP_FAILED);
        }
    }

    private static List<Header> createDefaultHeaders() {
        return Collections.singletonList(new BasicHeader("Authorization", "Bearer " + TrueNasConfig.TOKEN));
    }

    private static String buildUrl(String path, Map<String, String> params) {
        StringBuilder sb = new StringBuilder(TrueNasConfig.URL_PREFIX);
        sb.append(path);
        if (MapUtils.isNotEmpty(params)) {
            sb.append("?");
            params.forEach((k, v) -> {
                sb.append(k).append("=").append(v).append("&");
            });
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    private static <T> T parseResponse(CloseableHttpResponse response, Class<T> clazz) throws IOException {
        if (response.getStatusLine().getStatusCode() != 200) {
            throw exception(HTTP_FAILED);
        }
        String entity = EntityUtils.toString(response.getEntity());
        if (clazz == String.class) {
            return clazz.cast(entity);
        } else {
            return JSON.parseObject(entity, clazz);
        }
    }

    private static <T> List<T> parseListResponse(CloseableHttpResponse response, Class<T> clazz) throws IOException {
        if (response.getStatusLine().getStatusCode() != 200) {
            throw exception(HTTP_FAILED);
        }
        String entity = EntityUtils.toString(response.getEntity());
        return JSON.parseArray(entity, clazz);
    }

}
