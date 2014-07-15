/*
 * Copyright (c) 2013, tojsp.com and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.camel.framework.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Leo.du
 * @version 1.0
 * @since 1.0
 * @date 2013-8-6
 * 
 */
public final class IpUtils {

    private IpUtils() {

    }

    /**
     * 获取请求来源IP
     * 
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        } else {
            // 如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值
            String[] ips = StringUtils.split(ip, ",");
            if (ips != null && ips.length > 1) {
                for (String p : ips) {
                    if ("unknown".equalsIgnoreCase(p)) {
                        ip = p;
                        break;
                    }
                }
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        if (ip != null) {
            String[] ips = StringUtils.split(ip, ",");
            return ips[0];
        }
        return ip;
    }
}
