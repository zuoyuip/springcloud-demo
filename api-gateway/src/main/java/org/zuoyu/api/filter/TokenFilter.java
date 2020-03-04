package org.zuoyu.api.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 权限过滤.
 *
 * @author zuoyu
 * @program api-gateway
 * @create 2020-03-03 18:25
 **/
//@Component
public class TokenFilter extends ZuulFilter {

  @Override
  public String filterType() {
    return FilterConstants.PRE_TYPE;
  }

  @Override
  public int filterOrder() {
    return FilterConstants.PRE_DECORATION_FILTER_ORDER - 1;
  }

  @Override
  public boolean shouldFilter() {
    return true;
  }

  @Override
  public Object run() throws ZuulException {
    RequestContext requestContext = RequestContext.getCurrentContext();
    HttpServletRequest request = requestContext.getRequest();
    String token = request.getParameter("token");
    if (StringUtils.isEmpty(token)) {
      requestContext.setSendZuulResponse(false);
      requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
    }
    return null;
  }
}
