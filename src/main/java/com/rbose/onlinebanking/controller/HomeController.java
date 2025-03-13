package com.rbose.onlinebanking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.rbose.onlinebanking.entity.PrimaryAccount;
import com.rbose.onlinebanking.entity.SavingsAccount;
import com.rbose.onlinebanking.entity.User;
import com.rbose.onlinebanking.repository.RoleDao;
import com.rbose.onlinebanking.security.UserRole;
import com.rbose.onlinebanking.service.UserService;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;
/**
 * Created by Spring Tool Suite 4.
 * Project : online-banking
 * User: RitoBose
 * Email: ritankarbose2004@gmail.com
 * To change this template use File | Settings | File Templates.
 */

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;

    private final RoleDao roleDao;

    @GetMapping("/")
    public String home() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        User user = new User();

        model.addAttribute("user", user);

        return "signup";
    }

    @PostMapping("/signup")
    public String signupPost(@ModelAttribute("user") User user, Model model) {

        if (userService.checkUserExists(user.getUsername(), user.getEmail())) {

            if (userService.checkEmailExists(user.getEmail())) {
                model.addAttribute("emailExists", true);
            }

            if (userService.checkUsernameExists(user.getUsername())) {
                model.addAttribute("usernameExists", true);
            }

            return "signup";
        } else {
            Set<UserRole> userRoles = new HashSet<>();
            userRoles.add(new UserRole(user, roleDao.findByName("ROLE_USER")));

            userService.createUser(user, userRoles);

            return "redirect:/";
        }
    }

    @GetMapping("/userFront")
    public String userFront(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        PrimaryAccount primaryAccount = user.getPrimaryAccount();
        SavingsAccount savingsAccount = user.getSavingsAccount();

        model.addAttribute("primaryAccount", primaryAccount);
        model.addAttribute("savingsAccount", savingsAccount);

        return "userFront";
    }
}
