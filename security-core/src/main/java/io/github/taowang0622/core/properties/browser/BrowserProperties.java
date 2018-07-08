package io.github.taowang0622.core.properties.browser;

import io.github.taowang0622.core.properties.LoginType;

public class BrowserProperties {
    private String loginPage = "/default-login-page.html";

    private LoginType loginType = LoginType.JSON;

    private int rememberMeSeconds = 3600; //By default remember me for one hour!!

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public int getRememberMeSeconds() {
        return rememberMeSeconds;
    }

    public void setRememberMeSeconds(int rememberMeSeconds) {
        this.rememberMeSeconds = rememberMeSeconds;
    }
}
