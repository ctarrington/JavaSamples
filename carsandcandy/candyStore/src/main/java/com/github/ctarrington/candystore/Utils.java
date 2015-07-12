package com.github.ctarrington.candystore;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class Utils {

    public static void showCookies(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                System.out.println(cookie.getName() + " " + cookie.getValue());
            }
        }
    }
}
