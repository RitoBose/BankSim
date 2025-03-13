package com.rbose.onlinebanking.service;

import java.math.BigDecimal;
import java.security.Principal;

import com.rbose.onlinebanking.entity.PrimaryAccount;
import com.rbose.onlinebanking.entity.SavingsAccount;

/**
 * Created by Spring Tool Suite 4.
 * Project : online-banking
 * User: RitoBose
 * Email: ritankarbose2004@gmail.com
 * To change this template use File | Settings | File Templates.
 */
//@Service("accountService")
public interface AccountService {

    PrimaryAccount createPrimaryAccount();

    SavingsAccount createSavingsAccount();

    void deposit(String accountType, BigDecimal amount, Principal principal);

    void withdraw(String accountType, BigDecimal amount, Principal principal);

}
