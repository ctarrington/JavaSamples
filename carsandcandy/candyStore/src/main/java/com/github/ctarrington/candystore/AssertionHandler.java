package com.github.ctarrington.candystore;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AssertionHandler {

    @RequestMapping(value="/assertions", method= RequestMethod.POST)
    public String assertionSubmit(@RequestParam("content") String content, @RequestParam("redirectUrl") String redirectUrl, HttpServletResponse response) {
        response.addCookie(new Cookie("assertion", content));
        return "redirect:" + redirectUrl;
    }
}
