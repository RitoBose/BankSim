package com.rbose.onlinebanking.repository;

import org.springframework.data.repository.CrudRepository;

import com.rbose.onlinebanking.security.Role;

/**
 * Created by Spring Tool Suite 4.
 * Project : online-banking
 * User: RitoBose
 * Email: ritankarbose2004@gmail.com
 * To change this template use File | Settings | File Templates.
 */
public interface RoleDao extends CrudRepository<Role, Integer> {

    Role findByName(String name);
}