package com.rbose.onlinebanking.service.UserServiceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.rbose.onlinebanking.entity.PrimaryAccount;
import com.rbose.onlinebanking.entity.PrimaryTransaction;
import com.rbose.onlinebanking.entity.SavingsAccount;
import com.rbose.onlinebanking.entity.SavingsTransaction;
import com.rbose.onlinebanking.entity.User;
import com.rbose.onlinebanking.repository.PrimaryAccountDao;
import com.rbose.onlinebanking.repository.SavingsAccountDao;
import com.rbose.onlinebanking.repository.UserDao;
import com.rbose.onlinebanking.service.AccountService;
import com.rbose.onlinebanking.service.TransactionService;
//import com.rbose.onlinebanking.service.UserService;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;

/**
 * Created by Spring Tool Suite 4.
 * Project : online-banking
 * User: RitoBose
 * Email: ritankarbose2004@gmail.com
 * To change this template use File | Settings | File Templates.
 */
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private static int nextAccountNumber = 11223101;

    private final PrimaryAccountDao primaryAccountDao;

    private final SavingsAccountDao savingsAccountDao;

    private final UserDao userDao;

    private final TransactionService transactionService;

    public PrimaryAccount createPrimaryAccount() {
        PrimaryAccount primaryAccount = new PrimaryAccount();
        primaryAccount.setAccountBalance(new BigDecimal("0.0"));
        primaryAccount.setAccountNumber(accountGen());

        primaryAccountDao.save(primaryAccount);

        return primaryAccountDao.findByAccountNumber(primaryAccount.getAccountNumber());
    }

    public SavingsAccount createSavingsAccount() {
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setAccountBalance(new BigDecimal("0.0"));
        savingsAccount.setAccountNumber(accountGen());

        savingsAccountDao.save(savingsAccount);

        return savingsAccountDao.findByAccountNumber(savingsAccount.getAccountNumber());
    }

    public void deposit(String accountType, BigDecimal amount, Principal principal) {
        User user = userDao.findByUsername(principal.getName());

        if (accountType.equalsIgnoreCase("Primary")) {
            PrimaryAccount primaryAccount = user.getPrimaryAccount();
            primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().add(amount));
            primaryAccountDao.save(primaryAccount);

            Date date = new Date();

            PrimaryTransaction primaryTransaction = new PrimaryTransaction(date, "Deposit to Primary Account", "Account", "Finished", amount, primaryAccount.getAccountBalance(), primaryAccount);
            transactionService.savePrimaryDepositTransaction(primaryTransaction);

        } else if (accountType.equalsIgnoreCase("Savings")) {
            SavingsAccount savingsAccount = user.getSavingsAccount();
            savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().add(amount));
            savingsAccountDao.save(savingsAccount);

            Date date = new Date();
            SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Deposit to savings Account", "Account", "Finished", amount, savingsAccount.getAccountBalance(), savingsAccount);
            transactionService.saveSavingsDepositTransaction(savingsTransaction);
        }
    }

    public void withdraw(String accountType, BigDecimal amount, Principal principal) {
        User user = userDao.findByUsername(principal.getName());

        if (accountType.equalsIgnoreCase("Primary")) {
            PrimaryAccount primaryAccount = user.getPrimaryAccount();
            primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(amount));
            primaryAccountDao.save(primaryAccount);

            Date date = new Date();

            PrimaryTransaction primaryTransaction = new PrimaryTransaction(date, "Withdraw from Primary Account", "Account", "Finished", amount, primaryAccount.getAccountBalance(), primaryAccount);
            transactionService.savePrimaryWithdrawTransaction(primaryTransaction);
        } else if (accountType.equalsIgnoreCase("Savings")) {
            SavingsAccount savingsAccount = user.getSavingsAccount();
            savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(amount));
            savingsAccountDao.save(savingsAccount);

            Date date = new Date();
            SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Withdraw from savings Account", "Account", "Finished", amount, savingsAccount.getAccountBalance(), savingsAccount);
            transactionService.saveSavingsWithdrawTransaction(savingsTransaction);
        }
    }

    private int accountGen() {
        return ++nextAccountNumber;
    }


}
