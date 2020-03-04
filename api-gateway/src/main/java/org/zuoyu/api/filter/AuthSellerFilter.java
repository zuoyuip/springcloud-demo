package org.zuoyu.api.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.BooleanUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.zuoyu.api.constant.CookieConstant;
import org.zuoyu.api.constant.RedisConstant;
import org.zuoyu.api.utils.CookieUtil;

/**
 * 权限过滤.
 *
 * @author zuoyu
 * @program api-gateway
 * @create 2020-03-04 17:45
 **/
@Component
public class AuthSellerFilter extends ZuulFilter {

  private final StringRedisTemplate stringRedisTemplate;

  public AuthSellerFilter(StringRedisTemplate stringRedisTemplate) {
    this.stringRedisTemplate = stringRedisTemplate;
  }

  @Override
  public String filterType() {
    return FilterConstants.PRE_TYPE;
  }

  @Override
  public int filterOrder() {
    return FilterConstants.SERVLET_DETECTION_FILTER_ORDER - 1;
  }

  @Override
  public boolean shouldFilter() {
    RequestContext requestContext = RequestContext.getCurrentContext();
    HttpServletRequest request = requestContext.getRequest();
    return "/order/order/finish".equals(request.getRequestURI());
  }

  @Override
  public Object run() throws ZuulException {
    RequestContext requestContext = RequestContext.getCurrentContext();
    HttpServletRequest request = requestContext.getRequest();

    Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
    if (cookie == null || StringUtils.isEmpty(cookie.getValue()) || BooleanUtils
        .isFalse(stringRedisTemplate
            .hasKey(String.format(RedisConstant.TOKEN_TEMPLATE, cookie.getValue())))) {
      requestContext.setSendZuulResponse(false);
      requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
    }
    return null;
  }
}
