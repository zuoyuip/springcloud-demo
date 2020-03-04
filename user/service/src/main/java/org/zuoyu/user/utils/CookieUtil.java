package org.zuoyu.user.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.ArrayUtils;

/**
 * cookie工具.
 *
 * @author zuoyu
 * @program user
 * @create 2020-03-04 15:19
 **/
public class CookieUtil {

  public static Cookie get(HttpServletRequest request, String name) {
    Cookie[] cookies = request.getCookies();
    if (ArrayUtils.isNotEmpty(cookies)) {
      for (Cookie cookie : cookies) {
        if (name.equals(cookie.getName())) {
          return cookie;
        }
      }
    }
    return null;
  }

  public static void set(HttpServletResponse response, String name, String value, int maxAge) {
    Cookie cookie = new Cookie(name, value);
    cookie.setPath("/");
    cookie.setMaxAge(maxAge);
    response.addCookie(cookie);
  }
}
