package dev.oaiqiy.truenas.scale.sdk.service.cli;

import dev.oaiqiy.truenas.scale.sdk.exception.TrueNasExceptionErrorCode;
import dev.oaiqiy.truenas.scale.sdk.service.AbstractTrueNasService;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static dev.oaiqiy.truenas.scale.sdk.exception.TrueNasException.exception;


public abstract class AbstractCliTrueNasService extends AbstractTrueNasService {

    protected String run(String cmd) {
        try {
            Process process = new ProcessBuilder(cmd).start();
            String result = IOUtils.toString(process.getInputStream(), StandardCharsets.UTF_8);
            return result;
        } catch (IOException e) {
            throw exception(TrueNasExceptionErrorCode.RUN_CMD_FAILED);
        }
    }
}
