package com.bitbuy.test.ws.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import static java.util.Objects.nonNull;

@Controller
public class LoginController {

    @RequestMapping(
            value = { "/login"},
            method = RequestMethod.GET)
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        final Model model) {

        if (nonNull(error)) model.addAttribute("errorMessage",
                "Login failed, username or password is incorrect!");
        if (nonNull(logout)) model.addAttribute("logoutMessage",
                "You have been logged out!");

        return "/loginPage";
    }

}
