package org.zuoyu.api.filter;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
import lombok.SneakyThrows;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.zuoyu.api.exception.RateLimitException;

/**
 * 限流.
 *
 * @author zuoyu
 * @program api-gateway
 * @create 2020-03-03 20:17
 **/
@Component
public class RateLimitFilter extends ZuulFilter {

  private static final RateLimiter RATE_LIMITER = RateLimiter.create(1);

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
    return true;
  }

  @SneakyThrows
  @Override
  public Object run() throws ZuulException {
    if (!RATE_LIMITER.tryAcquire()){
      throw new RateLimitException();
    }
    return null;
  }
}
