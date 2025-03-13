package com.rbose.onlinebanking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;

import com.rbose.onlinebanking.entity.PrimaryAccount;
import com.rbose.onlinebanking.entity.PrimaryTransaction;
import com.rbose.onlinebanking.entity.SavingsAccount;
import com.rbose.onlinebanking.entity.SavingsTransaction;
import com.rbose.onlinebanking.entity.User;
import com.rbose.onlinebanking.service.AccountService;
import com.rbose.onlinebanking.service.TransactionService;
import com.rbose.onlinebanking.service.UserService;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

/**
 * Created by Spring Tool Suite 4.
 * Project : online-banking
 * User: RitoBose
 * Email: ritankarbose2004@gmail.com
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final UserService userService;

    private final AccountService accountService;

    private final TransactionService transactionService;

    @RequestMapping("/primaryAccount")
    public String primaryAccount(Model model, Principal principal) {
        List<PrimaryTransaction> primaryTransactionList = transactionService.findPrimaryTransactionList(principal.getName());

        User user = userService.findByUsername(principal.getName());
        PrimaryAccount primaryAccount = user.getPrimaryAccount();

        model.addAttribute("primaryAccount", primaryAccount);
        model.addAttribute("primaryTransactionList", primaryTransactionList);

        return "primaryAccount";
    }

    @RequestMapping("/savingsAccount")
    public String savingsAccount(Model model, Principal principal) {
        List<SavingsTransaction> savingsTransactionList = transactionService.findSavingsTransactionList(principal.getName());
        User user = userService.findByUsername(principal.getName());
        SavingsAccount savingsAccount = user.getSavingsAccount();

        model.addAttribute("savingsAccount", savingsAccount);
        model.addAttribute("savingsTransactionList", savingsTransactionList);

        return "savingsAccount";
    }

    @GetMapping("/deposit")
    public String deposit(Model model) {
        model.addAttribute("accountType", "");
        model.addAttribute("amount", "");

        return "deposit";
    }

    @PostMapping("/deposit")
    public String depositPOST(@ModelAttribute("amount") String amount, 
            @ModelAttribute("accountType") String accountType, 
            Principal principal) {
accountService.deposit(accountType, new BigDecimal(amount), principal);
return "redirect:/userFront";
}


    @GetMapping("/withdraw")
    public String withdraw(Model model) {
        model.addAttribute("accountType", "");
        model.addAttribute("amount", "");

        return "withdraw";
    }

    @PostMapping("/withdraw")
    public String withdrawPOST(@ModelAttribute("amount") String amount, @ModelAttribute("accountType") String accountType, Principal principal) {
        accountService.withdraw(accountType, new BigDecimal(amount), principal);

        return "redirect:/userFront";
    }
}
