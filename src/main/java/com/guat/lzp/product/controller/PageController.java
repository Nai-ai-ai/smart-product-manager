package com.guat.lzp.product.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 页面路由控制器
 */
@Controller
public class PageController {

    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/index")
    public String index2() {
        return "admin/index";
    }

    @GetMapping("/admin/welcome")
    public String adminWelcome() {
        return "admin/welcome";
    }

    @GetMapping("/admin/category")
    public String adminCategory() {
        return "admin/category";
    }

    @GetMapping("/admin/product")
    public String adminProduct() {
        return "admin/product";
    }

    @GetMapping("/user")
    public String userIndex() {
        return "user/index";
    }
}
