package com.rbose.onlinebanking.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by Spring Tool Suite 4.
 * Project : online-banking
 * User: RitoBose
 * Email: ritankarbose2004@gmail.com
 * To change this template use File | Settings | File Templates.
 */
public class Authority implements GrantedAuthority {

	private static final long serialVersionUID = 1L;
    private final String authority;

    public Authority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}