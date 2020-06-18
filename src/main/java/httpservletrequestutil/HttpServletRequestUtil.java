package httpservletrequestutil;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.StringTokenizer;

public class HttpServletRequestUtil {
    public static String obterIp(HttpServletRequest request) {
        if (request != null) {
            if (StringUtils.isBlank(request.getHeader("X-FORWARDED-FOR"))) {
                return request.getRemoteAddr();
            } else {
                return new StringTokenizer(request.getHeader("X-FORWARDED-FOR"), ",").nextToken().trim();
            }
        }

        return "";
    }

    public static String obterQuery(HttpServletRequest request) {
        return request != null && StringUtils.isNotBlank(request.getQueryString()) ? request.getQueryString() : "";
    }

    public static String obterPath(HttpServletRequest request) {
        return request != null && StringUtils.isNotBlank(request.getRequestURI()) ? request.getRequestURI() : "";
    }

    public static String obterPathQuery(HttpServletRequest request) {
        return request != null ? new StringBuilder(obterPath(request)).append(obterQuery(request)).toString() : "";
    }

    public static String obterFirstPath(HttpServletRequest request) {
        String[] paths = StringUtils.split(obterPath(request), "/");
        return paths.length <= 0 ? "" : paths[0];
    }
}
