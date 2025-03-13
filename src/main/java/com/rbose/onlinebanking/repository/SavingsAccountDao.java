package com.rbose.onlinebanking.repository;

import org.springframework.data.repository.CrudRepository;

import com.rbose.onlinebanking.entity.SavingsAccount;

/**
 * Created by Spring Tool Suite 4.
 * Project : online-banking
 * User: RitoBose
 * Email: ritankarbose2004@gmail.com
 * To change this template use File | Settings | File Templates.
 */
public interface SavingsAccountDao extends CrudRepository<SavingsAccount, Long> {

    SavingsAccount findByAccountNumber(int accountNumber);
}