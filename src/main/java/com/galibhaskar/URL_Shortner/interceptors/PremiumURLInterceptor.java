package com.galibhaskar.URL_Shortner.interceptors;


import com.galibhaskar.URL_Shortner.concerns.IHelperService;
import com.galibhaskar.URL_Shortner.models.PremiumBody;
import com.galibhaskar.URL_Shortner.models.ShortURL;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

@Component
public class PremiumURLInterceptor implements HandlerInterceptor {
    private final IHelperService helperService;

    public PremiumURLInterceptor(@Autowired IHelperService helperService) {
        this.helperService = helperService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler)
            throws Exception {

        System.out.println("premium URL interceptor pre-handle");

        String requestBody = helperService.extractRequestBody(request);

        PremiumBody deserializedBody = helperService.deserializeJSONString(requestBody, PremiumBody.class);

        if (!Objects.equals(deserializedBody.getUserType(), "pro")) {
            response.sendError(HttpStatus.FORBIDDEN.value(), "Not a Premium User");
            return false;
        }

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
