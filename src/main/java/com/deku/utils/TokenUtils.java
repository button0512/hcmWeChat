package com.deku.utils;

import com.alibaba.fastjson.JSONObject;
import com.deku.Application;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;

public class TokenUtils {

    private static HashMap<String, String> cache = new HashMap<String, String>();
    private static final String tokenKey = "token";
    private static final String tokenExpireKey = "expire";

    public static synchronized String getToken() {
        String token = cache.get(tokenKey);
        String tokenExpire = cache.get(tokenExpireKey);
        if (StringUtils.isNotBlank(token)) {
            if (System.currentTimeMillis() - Long.valueOf(tokenExpire) <= 1000 * 60 * 60) {
                return token;
            }
        }


        //请求token接口
        HttpClientUtilsRespose tokenResponse = HttpClientUtils.get(Application.CRM_DOMAIN + "/security/getToken?appKey=test&appSecret=test");
        String responseString = tokenResponse.getResponseString();
        System.out.println("get token = " + responseString);
        JSONObject js = (JSONObject) JSONObject.parse(responseString);
        token = (String) js.getJSONObject("responseDomain").get("token");


        cache.put(tokenKey, token);
        cache.put(tokenExpireKey, System.currentTimeMillis() + "");
        return token;
    }

}
