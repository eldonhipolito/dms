package com.github.com.eldonhipolito.dms.config;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LoggingInterceptor extends HandlerInterceptorAdapter {

  @Override
  public void afterCompletion(
      HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception)
      throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public void postHandle(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler,
      ModelAndView modelAndView)
      throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    log.info("[BEFORE] - {}", request.getParameter("file"));
    log.info("[BEFORE] - {}", request.getParameter("document"));
    
    
    request
        .getParameterMap()
        .entrySet()
        .forEach(i -> log.info("K{}-V{}", i.getKey(), i.getValue()));
    return true;
  }
}
