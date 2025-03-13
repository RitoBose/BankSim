package com.rbose.onlinebanking.repository;

import org.springframework.data.repository.CrudRepository;

import com.rbose.onlinebanking.entity.SavingsTransaction;

import java.util.List;

/**
 * Created by Spring Tool Suite 4.
 * Project : online-banking
 * User: RitoBose
 * Email: ritankarbose2004@gmail.com
 * To change this template use File | Settings | File Templates.
 */
public interface SavingsTransactionDao extends CrudRepository<SavingsTransaction, Long> {

    List<SavingsTransaction> findAll();
}