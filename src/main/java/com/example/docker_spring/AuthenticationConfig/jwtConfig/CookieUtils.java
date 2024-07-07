package com.example.docker_spring.AuthenticationConfig.jwtConfig;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CookieUtils {

    @Value("${app.accessCookieExpiration}")
    private int accessCookieExpiration;

    @Value("${app.refreshCookieExpiration}")
    private int refreshCookieExpiration;

    public void createCookie(HttpServletResponse response, String name, String value, int maxAge, boolean httpOnly,
                             boolean secure, String path) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setHttpOnly(httpOnly);
        cookie.setSecure(secure);
        cookie.setPath(path);
        response.addCookie(cookie);
    }

    public void deleteCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    cookie.setValue(null);
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    break;
                }
            }
        }
    }

    public void createAccessTokenCookie(HttpServletResponse response, String accessToken) {
        createCookie(response, "accessToken", accessToken, accessCookieExpiration, true, false, "/");
    }

    public void createRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        createCookie(response, "refreshToken", refreshToken, refreshCookieExpiration, true, false, "/");
    }
    public String getRefreshTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refreshToken")) {
                    String accessToken = cookie.getValue();
                    return accessToken;
                }
            }
        }
        return null;
    }
}
