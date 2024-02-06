package com.joosangah.activityservice;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import javax.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class FeignClientConfiguration {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new FeignClientInterceptor();
    }

    private static class FeignClientInterceptor implements RequestInterceptor {

        @Override
        public void apply(RequestTemplate template) {
            // 현재 요청의 헤더에서 Access Token을 가져와서 Feign 요청에 추가
            String accessToken = extractAccessToken();
            if (accessToken != null) {
                template.header("Authorization", accessToken);
            }
        }

        private String extractAccessToken() {
            // 현재 요청의 헤더에서 Access Token을 가져오는 로직
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (requestAttributes != null) {
                // HttpServletRequest를 통해 Access Token을 가져옴
                HttpServletRequest request = requestAttributes.getRequest();
                return request.getHeader("Authorization");
            }
            return null;
        }
    }
}