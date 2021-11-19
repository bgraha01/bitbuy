package com.bitbuy.test.ws.controllers;

import com.bitbuy.test.domain.exceptions.UserAlreadyExistsException;
import com.bitbuy.test.domain.UserData;
import com.bitbuy.test.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegistrationController {

    private final UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String registrationPage() {
        return "/registrationPage";
    }

    @ModelAttribute("userData")
    public UserData userData() {
        return new UserData();
    }

    @RequestMapping(method = RequestMethod.POST)
    public String registerUser(@Valid UserData userData,
                               Model model,
                               BindingResult bindingResult) {
        try {
            userService.register(userData);
        } catch (UserAlreadyExistsException e){
            bindingResult.rejectValue("userName",
                    "",
                    "Username " + e.getUserName() + " is already registered!");
            model.addAttribute("registrationForm", userData);
            return "registrationPage";
        }
        return "redirect:/register?success";
    }

}
