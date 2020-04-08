package com.deku.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.ArrayUtils;

import java.io.UnsupportedEncodingException;

/**
 * HttpUtil工具类返回结果封装
 *
 * @author danly.feng
 * @create 2018-01-03 19:07
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Log4j2
public class HttpClientUtilsRespose {

    private int status;
    private byte[] responseBytes;

    public String getResponseString() {
        try {
            if (ArrayUtils.isNotEmpty(responseBytes)) {
                return new String(responseBytes, "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            log.error("Unsupported Encoding", e);
        }
        return null;
    }

    @Override
    public String toString() {
        return "{" +
                "status=" + status +
                ", response=" + getResponseString() +
                '}';
    }

    public boolean isSuccess() {
        return 200 == status;
    }

}
