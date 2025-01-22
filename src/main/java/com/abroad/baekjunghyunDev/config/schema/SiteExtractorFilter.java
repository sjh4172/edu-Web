package com.abroad.baekjunghyunDev.config.schema;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.abroad.baekjunghyunDev.service.SiteService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SiteExtractorFilter extends OncePerRequestFilter {

    private final SiteService siteService;

    public SiteExtractorFilter(SiteService siteService) {
        this.siteService = siteService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String uri = request.getRequestURI(); // 예: /example/v1/loginProc
        String siteValue = extractSiteFromURI(uri);

        if (siteValue != null) {
            // site 값을 데이터베이스에 저장
            siteService.saveSite(siteValue);
            System.out.println("Saved Site: " + siteValue);
        }

        filterChain.doFilter(request, response);
    }

    private String extractSiteFromURI(String uri) {
        // URL에서 "/v1/{site}/loginProc" 패턴의 site 값 추출
        String[] parts = uri.split("/");
        // parts 배열 구조: ["", "v1", "{site}", "loginProc"]
        return (parts.length > 2 && "v1".equals(parts[1]) && "loginProc".equals(parts[3])) ? parts[2] : null;
    }
}