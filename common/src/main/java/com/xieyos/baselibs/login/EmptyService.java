package com.xieyos.baselibs.login;

import com.xieyos.baselibs.login.LoginService;

public class EmptyService implements LoginService {
    @Override
    public boolean isLogin() {
        return false;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUserName() {
        return null;
    }
}
