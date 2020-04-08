package com.deku.utils;


import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;

import javax.net.ssl.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

/**
 * Http协议访问工具类，如需写SDK请继承此类
 *
 * @author danly.feng
 * @create 2018-08-19 11:40
 **/
@Log4j2
public abstract class HttpClientUtils {

    public static final String CONTENT_TYPE_APPLICATION_JSON = "application/json";
    public static final String CONTENT_TYPE_APPLICATION_XWWWFORM = "application/x-www-form-urlencoded";
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static PoolingHttpClientConnectionManager cm = null;

    static {
        if (cm == null) {
            SSLContext sc = null;

            try {
                sc = SSLContext.getInstance("SSLv3");
            } catch (NoSuchAlgorithmException var3) {
                var3.printStackTrace();
            }

            try {
                sc.init((KeyManager[]) null, new TrustManager[]{new X509TrustManager() {
                    public void checkClientTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString) throws CertificateException {
                    }

                    public void checkServerTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString) throws CertificateException {
                    }

                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                }}, (SecureRandom) null);
            } catch (KeyManagementException var2) {
                var2.printStackTrace();
            }
            Registry socketFactoryRegistry = RegistryBuilder.create().register("http", PlainConnectionSocketFactory.INSTANCE).register("https", new SSLConnectionSocketFactory(sc, new HostnameVerifier() {
                public boolean verify(String s, SSLSession sslSession) {
                    return true;
                }
            })).build();

            cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            cm.setMaxTotal(500);
            cm.setDefaultMaxPerRoute(20);
        }
    }


    /**
     * 获取HttpRequest的配置对象
     *
     * @return RequestConfig对象
     */
    private static RequestConfig getRequestConfig() {
        return RequestConfig.custom().setConnectTimeout(100000000).setConnectionRequestTimeout(100000000).setSocketTimeout(100000000).build();
    }

    /**
     * 按get方式提交url
     *
     * @param url 需要访问的URL地址
     * @return 访问url后返回的内容
     */
    public static HttpClientUtilsRespose get(String url) {
        HttpGet httpGet = new HttpGet(url);
        return getResult(httpGet);
    }

    /**
     * 按get方式提交url
     *
     * @param url    需要访问的URL地址
     * @param params get请求参数
     * @return 访问url后返回的内容
     */
    public static HttpClientUtilsRespose get(String url, String... params) {
        if (params.length % 2 != 0) {
            throw new IllegalArgumentException("Invalid number of parameters; each name must have a corresponding value!");
        }

        url = url.concat("?");
        for (int i = 0; i < params.length; i += 2) {
            if (StringUtils.isNotBlank(params[i]) && StringUtils.isNotBlank(params[i + 1])) {
                url = url.concat(params[i]).concat("=").concat(params[i + 1]).concat("&");
            }
        }

        url = url.substring(0, url.length() - 1);
        return get(url);
    }

    /**
     * 按get方式提交url
     *
     * @param url     需要访问的URL地址
     * @param params  get请求参数
     * @param headers get请求头
     * @return 访问url后返回的内容
     */
    public static HttpClientUtilsRespose get(String url, String[] params, String[] headers) {
        if (params.length % 2 != 0) {
            throw new IllegalArgumentException("Invalid number of parameters; each name must have a corresponding value!");
        }
        if (headers.length % 2 != 0) {
            throw new IllegalArgumentException("Invalid number of headers; each name must have a corresponding value!");
        }

        url = url.concat("?");
        for (int i = 0; i < params.length; i += 2) {
            if (StringUtils.isNotBlank(params[i]) && StringUtils.isNotBlank(params[i + 1])) {
                url = url.concat(params[i]).concat("=").concat(params[i + 1]).concat("&");
            }
        }
        url = url.substring(0, url.length() - 1);

        HttpGet httpGet = new HttpGet(url);

        for (int i = 0; i < headers.length; i += 2) {
            if (headers[i] == null || headers[i + 1] == null)
                continue;
            httpGet.addHeader(headers[i], headers[i + 1]);
        }

        return getResult(httpGet);
    }

    /**
     * 按post方式提交url
     *
     * @param url    需要访问的URL地址
     * @param params 表单key-value参数
     * @return 访问url后返回的内容
     * @throws Exception
     */
    public static HttpClientUtilsRespose post(String url, String[] params) {
        if (params.length % 2 != 0) {
            throw new IllegalArgumentException("Invalid number of parameters; each name must have a corresponding value!");
        }

        ArrayList<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
        for (int i = 0; i < params.length; i += 2) {
            if (params[i] == null || params[i + 1] == null)
                continue;
            list.add(new BasicNameValuePair(params[i], params[i + 1]));
        }

        HttpPost httpPost = new HttpPost(url);
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(list, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            log.error("", e);
            return new HttpClientUtilsRespose();
        }
        return getResult(httpPost);
    }

    /**
     * 按post方式提交url
     *
     * @param url 需要访问的URL地址
     * @return 访问url后返回的内容
     * @throws Exception
     */
    public static HttpClientUtilsRespose post(String url) {
        HttpPost httpPost = new HttpPost(url);
        return getResult(httpPost);
    }

    /**
     * 按post方式提交url
     *
     * @param url  需要访问的URL地址
     * @param body 请求消息体
     * @return 访问url后返回的内容
     * @throws Exception
     */
    public static HttpClientUtilsRespose post(String url, String body) {
        HttpPost httpPost = new HttpPost(url);
        StringEntity se = new StringEntity(body, DEFAULT_CHARSET);
        se.setContentType(CONTENT_TYPE_APPLICATION_JSON);
        httpPost.setEntity(se);
        return getResult(httpPost);
    }

    /**
     * 按post方式提交url
     *
     * @param url     需要访问的URL地址
     * @param body    请求消息体
     * @param headers 请求头
     * @return 访问url后返回的内容
     * @throws Exception
     */
    public static HttpClientUtilsRespose post(String url, String body, String... headers) {
        if (headers.length % 2 != 0) {
            throw new IllegalArgumentException("Invalid number of headers; each name must have a corresponding value!");
        }

        HttpPost httpPost = new HttpPost(url);

        for (int i = 0; i < headers.length; i += 2) {
            if (headers[i] == null || headers[i + 1] == null)
                continue;
            httpPost.addHeader(headers[i], headers[i + 1]);
        }

        if (StringUtils.isNotBlank(body)) {
            StringEntity se = new StringEntity(body, DEFAULT_CHARSET);
            se.setContentType(CONTENT_TYPE_APPLICATION_JSON);
            httpPost.setEntity(se);
        }
        return getResult(httpPost);
    }

    /**
     * post请求
     *
     * @param url         url地址
     * @param body        请求消息体
     * @param contentType 请求数据类型
     * @param headers     请求头
     * @return
     */
    public static HttpClientUtilsRespose post(String url, String body, String contentType, String... headers) {
        if (headers.length % 2 != 0) {
            throw new IllegalArgumentException("Invalid number of headers; each name must have a corresponding value!");
        }

        HttpPost httpPost = new HttpPost(url);

        for (int i = 0; i < headers.length; i += 2) {
            if (headers[i] == null || headers[i + 1] == null)
                continue;
            httpPost.addHeader(headers[i], headers[i + 1]);
        }

        StringEntity se = new StringEntity(body, DEFAULT_CHARSET);
        se.setContentType(contentType);
        httpPost.setEntity(se);
        return getResult(httpPost);
    }

    private static HttpClientUtilsRespose getResult(HttpRequestBase request) {
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).setConnectionManagerShared(true).build();
        request.setConfig(getRequestConfig());
        try {
            CloseableHttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                byte[] buff = inputStreamToByte(instream);
                return new HttpClientUtilsRespose(response.getStatusLine().getStatusCode(), buff);
            }
        } catch (Exception e) {
           log.error("HttpRequest get result error", e);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                log.error("", e);
            }

        }
        return new HttpClientUtilsRespose();
    }

    private static byte[] inputStreamToByte(InputStream is) throws IOException {
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int ch;
        while ((ch = is.read(buffer)) != -1) {
            bytestream.write(buffer, 0, ch);
        }
        byte data[] = bytestream.toByteArray();
        bytestream.close();
        return data;
    }

}
