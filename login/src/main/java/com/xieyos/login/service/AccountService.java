package com.xieyos.login.service;

import com.xieyos.baselibs.login.LoginService;

public class AccountService implements LoginService {

    private boolean login;
    private String password;

    public AccountService(boolean login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public boolean isLogin() {
        return login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUserName() {
        return null;
    }
}
