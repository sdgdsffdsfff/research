/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.trainreserve;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.perf4j.LoggingStopWatch;
import org.perf4j.StopWatch;

import com.camel.utils.MD5;

/**
 * 使用jdk 原生实现
 * 
 * @author dengqb
 * @date 2014年12月20日
 */
public class JDKHttpsClient {
    private static final Logger log = Logger.getLogger(JDKHttpsClient.class);
    
    private static final String METHOD_POST = "POST";
    private static final String DEFAULT_CHARSET = "utf-8";
    
    public static String doGet(String url){
        InputStream in = null;
        BufferedReader br = null;
        StringBuffer str_return = new StringBuffer();
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[] { new DefaultTrustManager() }, new java.security.SecureRandom());
            URL console = new URL(url);
            HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
            conn.setSSLSocketFactory(sc.getSocketFactory());
            conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
            conn.connect();
            in = conn.getInputStream();
            br = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while ((line = br.readLine()) != null){
                str_return = str_return.append(line);
            }
            conn.disconnect();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (KeyManagementException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                br.close();
                in.close();
            } catch (Exception e) {
            }
        }
        return str_return.toString();
    }
    
    public static ByteArrayOutputStream doGetImg(String url,String cookieStr){
        InputStream in = null;
        ByteArrayOutputStream outStream = null;
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[] { new DefaultTrustManager() }, new SecureRandom());
            
            URL console = new URL(url);
            HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
            conn.setRequestProperty("Cookie", cookieStr);
            conn.setSSLSocketFactory(sc.getSocketFactory());
            conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
            conn.connect();
            in = conn.getInputStream();
            outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) != -1){
                outStream.write(buffer, 0, len);
            }
            conn.disconnect();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (KeyManagementException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (Exception e) {
            }
        }
        return outStream;
    }
    
    public static String doPost(String url, String cookieStr, String params, String charset, int connectTimeout, int readTimeout)
            throws Exception {
        String ctype = "application/json;charset=" + charset;
        byte[] content = {};
        if (params != null) {
            content = params.getBytes(charset);
        }

        return doPost(url, cookieStr, ctype, content, connectTimeout, readTimeout);
    }

    public static String doPost(String url, String cookieStr, String ctype, byte[] content, int connectTimeout, int readTimeout)
            throws Exception {
        HttpsURLConnection conn = null;
        OutputStream out = null;
        String rsp = null;
        try {
            try {
                SSLContext ctx = SSLContext.getInstance("TLS");
                ctx.init(null, new TrustManager[] { new DefaultTrustManager() }, new SecureRandom());
                //SSLContext.setDefault(ctx);

                conn = getConnection(new URL(url), METHOD_POST, ctype);
                conn.setSSLSocketFactory(ctx.getSocketFactory());
                conn.setRequestProperty("Cookie", cookieStr);
                conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
                conn.setConnectTimeout(connectTimeout);
                conn.setReadTimeout(readTimeout);
            } catch (Exception e) {
                log.error("GET_CONNECTOIN_ERROR, URL = " + url, e);
                throw e;
            }
            try {
                out = conn.getOutputStream();
                out.write(content);
                rsp = getResponseAsString(conn);
            } catch (IOException e) {
                log.error("REQUEST_RESPONSE_ERROR, URL = " + url, e);
                throw e;
            }

        } finally {
            if (out != null) {
                out.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }

        return rsp;
    }
    
    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
    
    private static class DefaultTrustManager implements X509TrustManager {
        @Override
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        @Override
        public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
                throws java.security.cert.CertificateException {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
                throws java.security.cert.CertificateException {
            // TODO Auto-generated method stub
            
        }

    }

    private static HttpsURLConnection getConnection(URL url, String method, String ctype) throws IOException {
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod(method);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestProperty("Accept", "text/xml,text/javascript,text/html");
        conn.setRequestProperty("User-Agent", "stargate");
        conn.setRequestProperty("Content-Type", ctype);
        return conn;
    }

    protected static String getResponseAsString(HttpURLConnection conn) throws IOException {
        String charset = getResponseCharset(conn.getContentType());
        InputStream es = conn.getErrorStream();
        if (es == null) {
            return getStreamAsString(conn.getInputStream(), charset);
        } else {
            String msg = getStreamAsString(es, charset);
            if (StringUtils.isEmpty(msg)) {
                throw new IOException(conn.getResponseCode() + ":" + conn.getResponseMessage());
            } else {
                throw new IOException(msg);
            }
        }
    }

    private static String getStreamAsString(InputStream stream, String charset) throws IOException {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, charset));
            StringWriter writer = new StringWriter();

            char[] chars = new char[256];
            int count = 0;
            while ((count = reader.read(chars)) > 0) {
                writer.write(chars, 0, count);
            }

            return writer.toString();
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

    private static String getResponseCharset(String ctype) {
        String charset = DEFAULT_CHARSET;

        if (!StringUtils.isEmpty(ctype)) {
            String[] params = ctype.split(";");
            for (String param : params) {
                param = param.trim();
                if (param.startsWith("charset")) {
                    String[] pair = param.split("=", 2);
                    if (pair.length == 2) {
                        if (!StringUtils.isEmpty(pair[1])) {
                            charset = pair[1].trim();
                        }
                    }
                    break;
                }
            }
        }

        return charset;
    }

    public static void main (String[] args){
        JDKHttpsClient client = new JDKHttpsClient();
        String url = "https://kyfw.12306.cn/otn/leftTicket/queryT?leftTicketDTO.train_date=2015-02-17&leftTicketDTO.from_station=SZQ&leftTicketDTO.to_station=SYQ&purpose_codes=ADULT";
        try {
            StopWatch stopWatch = new LoggingStopWatch("request 12306");
            String rs = JDKHttpsClient.doGet(url);
            System.out.println(rs);
            System.out.println(MD5.GetMD5Code(rs.getBytes()));
            stopWatch.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
