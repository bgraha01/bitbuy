package com.bitbuy.test.ws.controllers;

import com.bitbuy.test.domain.UserData;
import com.bitbuy.test.service.UserService;
import com.bitbuy.test.ws.persistence.entities.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.UUID;

@Controller
@AllArgsConstructor
public class UserAccountController {

    private final UserService userService;

    @RequestMapping(
            value = "/users/{id}",
            method = RequestMethod.GET)
    @PreAuthorize(value = "@userCheck.youAreWhoYouSay(authentication, #id)")
    public String userInfo(final Model model,
                           @PathVariable UUID id) {
        UserEntity user = userService.findUserById(id).orElse(new UserEntity());
        model.addAttribute("userId", id.toString());
        model.addAttribute("userName", user.getUsername());
        model.addAttribute("name", user.getName());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("phone", user.getPhone());
        return "/home";
    }

    @RequestMapping(
            value = "/users/{id}",
            method = RequestMethod.POST)
    @PreAuthorize(value = "@userCheck.youAreWhoYouSay(authentication, #id)")
    public String updateUserInfo(final Model model,
                                 @PathVariable UUID id,
                                 UserData userData) {
        userService.findUserById(id).map(user -> {
            model.addAttribute("userId", id.toString());
            model.addAttribute("userName", user.getUsername());

            String name = userData.getName();
            user.setName(name);
            model.addAttribute("name", name);

            String email = userData.getEmail();
            user.setEmail(email);
            model.addAttribute("email", email);

            String phone = userData.getPhone();
            user.setPhone(phone);
            model.addAttribute("phone", phone);
            return user;
        }).ifPresent(userService::saveUser);

        return "redirect:/users/" + id + "?success";
    }

    @RequestMapping(
            value = "/users/accessDenied",
            method = RequestMethod.GET)
    public String userAccessDenied() {
        return "/accessDenied";
    }

    @ModelAttribute("userData")
    public UserData userData() {
        return new UserData();
    }

}
