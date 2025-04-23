package com.xiaomi.user;

public interface UserService {
    //登录
    User loginService(String uname, String password);

    //注册
    User registService(User user);
}
