package com.galibhaskar.URL_Shortner.interceptors;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.galibhaskar.URL_Shortner.concerns.IHelperService;
import com.galibhaskar.URL_Shortner.models.PremiumBody;
import com.galibhaskar.URL_Shortner.models.ShortURL;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

@Component
public class URLInterceptor implements HandlerInterceptor {
    private final IHelperService helperService;

    public URLInterceptor(@Autowired IHelperService helperService) {
        this.helperService = helperService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler)
            throws Exception {
        System.out.println("interceptor pre-handle");

        String requestBody = helperService.extractRequestBody(request);

        request.setAttribute("requestBody", requestBody);

        return true;

//        get body
//        body -> string -> class object-> object mapper -> validation
//            if()
//                request.setAttribute("isValid", true);
//            else
//                request.setAttribute("isValid", false);
//
//        allow request to controller
//        deny the request
//        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("interceptor post-handle");
    }
}
