package org.jan.webapp.hms.util;

import javax.servlet.http.HttpServletRequest;

import org.jan.common.utils.lang.StringUtils;

/**
 * @author Jan.Wang
 *
 */
public final class IpUtil {

    private static final String[] HEADERS_NAME = {"x-forwarded-for", "Proxy-Client-IP", "WL-Proxy-Client-IP"};

    private static final String UNKNOWN = "unknown";
    private static final String LOCAL = "Local";
    private static final String LOCAL_KEYWORDS = "0:";

    private IpUtil(){}

    public static String getIpAddr(HttpServletRequest request) {
        String ip = null;
        for(String headerName : HEADERS_NAME){
            ip = request.getHeader(headerName);
            if(checkIp(ip))
                break;
        }
        if(!checkIp(ip))
            ip = request.getRemoteAddr();
        if (ip.contains(LOCAL_KEYWORDS))
            ip = LOCAL;
        return ip;
    }

    private static boolean checkIp(String ip){
        return !StringUtils.isEmpty(ip) && !UNKNOWN.equalsIgnoreCase(ip);
    }
}
