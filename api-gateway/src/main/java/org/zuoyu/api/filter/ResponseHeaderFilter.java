package org.zuoyu.api.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import java.util.UUID;
import javax.servlet.http.HttpServletResponse;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

/**
 * 返回头校验.
 *
 * @author zuoyu
 * @program api-gateway
 * @create 2020-03-03 19:16
 **/
@Component
public class ResponseHeaderFilter extends ZuulFilter {

  @Override
  public String filterType() {
    return FilterConstants.POST_TYPE;
  }

  @Override
  public int filterOrder() {
    return FilterConstants.SEND_ERROR_FILTER_ORDER - 1;
  }

  @Override
  public boolean shouldFilter() {
    return true;
  }

  @Override
  public Object run() throws ZuulException {
    RequestContext requestContext = RequestContext.getCurrentContext();
    HttpServletResponse response = requestContext.getResponse();
    response.setHeader("X-Foo", UUID.randomUUID().toString());
    return null;
  }
}
