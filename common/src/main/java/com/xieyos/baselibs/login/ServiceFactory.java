package com.xieyos.baselibs.login;

import android.util.Log;

public class ServiceFactory {
    private LoginService loginService;
    private ServiceFactory() {

    }

    public static ServiceFactory getInstance() {
        return Inner.serviceFactory;
    }

    private static class Inner {
        private static ServiceFactory serviceFactory = new ServiceFactory();
    }

    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    public LoginService getLoginService() {
        if (loginService == null) {
            return new EmptyService();
        }else {
            return loginService;
        }
    }
}
