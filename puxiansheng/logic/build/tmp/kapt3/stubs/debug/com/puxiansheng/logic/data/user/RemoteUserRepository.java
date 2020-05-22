package com.puxiansheng.logic.data.user;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0007J\u001c\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00042\u0006\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u000eJ\u001e\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0010\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\u0007J<\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0010\u001a\u00020\u00072\b\u0010\u0012\u001a\u0004\u0018\u00010\u00072\b\u0010\u0013\u001a\u0004\u0018\u00010\u00072\b\u0010\u0014\u001a\u0004\u0018\u00010\u00072\b\u0010\u0015\u001a\u0004\u0018\u00010\u0007J\u001e\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0010\u001a\u00020\u00072\b\u0010\u0012\u001a\u0004\u0018\u00010\u0007J\u001e\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0010\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\u0007J\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0019\u001a\u00020\u0007J\u0012\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001c0\u001b0\u0004J\u001e\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0010\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\u0007J\u0012\u0010\u001e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001f0\u001b0\u0004J\"\u0010 \u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001c0\u001b0\u00042\u0006\u0010!\u001a\u00020\u00072\u0006\u0010\u0013\u001a\u00020\u0007J\u001a\u0010\"\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001c0\u001b0\u00042\u0006\u0010#\u001a\u00020\u0007JJ\u0010$\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001c0\u001b0\u00042\u0006\u0010%\u001a\u00020\u00072\u0006\u0010&\u001a\u00020\u00072\u0006\u0010\'\u001a\u00020\u00072\n\b\u0002\u0010(\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010)\u001a\u0004\u0018\u00010\u00072\u0006\u0010*\u001a\u00020\u0007\u00a8\u0006+"}, d2 = {"Lcom/puxiansheng/logic/data/user/RemoteUserRepository;", "", "()V", "bindMobileNumber", "Lcom/puxiansheng/util/http/APIRst;", "Lokhttp3/Response;", "phone", "", "code", "id", "favorite", "Lcom/puxiansheng/logic/bean/http/HttpRespFavorite;", "objectID", "type", "", "forgetPassword", "userAccount", "login", "userPassword", "newPassword", "verificationCode", "wechatCode", "loginByPass", "loginByPhoneNum", "loginByWeChat", "weChatCode", "logout", "Lcom/puxiansheng/util/http/APIResp;", "Lcom/puxiansheng/logic/bean/http/HttpRespEmpty;", "register", "requireRemoteUserInfo", "Lcom/puxiansheng/logic/bean/http/HttpRespUserInfo;", "resetPassword", "originalPassword", "submitSuggestion", "content", "submitUserInfo", "actualName", "sex", "nickName", "headerImg", "address", "cityId", "logic_debug"})
public final class RemoteUserRepository {
    
    @org.jetbrains.annotations.NotNull()
    public final com.puxiansheng.util.http.APIRst<okhttp3.Response> login(@org.jetbrains.annotations.NotNull()
    java.lang.String userAccount, @org.jetbrains.annotations.Nullable()
    java.lang.String userPassword, @org.jetbrains.annotations.Nullable()
    java.lang.String newPassword, @org.jetbrains.annotations.Nullable()
    java.lang.String verificationCode, @org.jetbrains.annotations.Nullable()
    java.lang.String wechatCode) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.puxiansheng.util.http.APIRst<okhttp3.Response> register(@org.jetbrains.annotations.NotNull()
    java.lang.String userAccount, @org.jetbrains.annotations.Nullable()
    java.lang.String code) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.puxiansheng.util.http.APIRst<okhttp3.Response> forgetPassword(@org.jetbrains.annotations.NotNull()
    java.lang.String userAccount, @org.jetbrains.annotations.Nullable()
    java.lang.String code) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.puxiansheng.util.http.APIRst<okhttp3.Response> loginByPass(@org.jetbrains.annotations.NotNull()
    java.lang.String userAccount, @org.jetbrains.annotations.Nullable()
    java.lang.String userPassword) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.puxiansheng.util.http.APIRst<okhttp3.Response> loginByPhoneNum(@org.jetbrains.annotations.NotNull()
    java.lang.String userAccount, @org.jetbrains.annotations.Nullable()
    java.lang.String code) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.puxiansheng.util.http.APIRst<okhttp3.Response> loginByWeChat(@org.jetbrains.annotations.NotNull()
    java.lang.String weChatCode) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.puxiansheng.util.http.APIRst<com.puxiansheng.util.http.APIResp<com.puxiansheng.logic.bean.http.HttpRespEmpty>> logout() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.puxiansheng.util.http.APIRst<com.puxiansheng.util.http.APIResp<com.puxiansheng.logic.bean.http.HttpRespEmpty>> resetPassword(@org.jetbrains.annotations.NotNull()
    java.lang.String originalPassword, @org.jetbrains.annotations.NotNull()
    java.lang.String newPassword) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.puxiansheng.util.http.APIRst<com.puxiansheng.util.http.APIResp<com.puxiansheng.logic.bean.http.HttpRespUserInfo>> requireRemoteUserInfo() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.puxiansheng.util.http.APIRst<com.puxiansheng.logic.bean.http.HttpRespFavorite> favorite(@org.jetbrains.annotations.NotNull()
    java.lang.String objectID, int type) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.puxiansheng.util.http.APIRst<com.puxiansheng.util.http.APIResp<com.puxiansheng.logic.bean.http.HttpRespEmpty>> submitUserInfo(@org.jetbrains.annotations.NotNull()
    java.lang.String actualName, @org.jetbrains.annotations.NotNull()
    java.lang.String sex, @org.jetbrains.annotations.NotNull()
    java.lang.String nickName, @org.jetbrains.annotations.Nullable()
    java.lang.String headerImg, @org.jetbrains.annotations.Nullable()
    java.lang.String address, @org.jetbrains.annotations.NotNull()
    java.lang.String cityId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.puxiansheng.util.http.APIRst<com.puxiansheng.util.http.APIResp<com.puxiansheng.logic.bean.http.HttpRespEmpty>> submitSuggestion(@org.jetbrains.annotations.NotNull()
    java.lang.String content) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.puxiansheng.util.http.APIRst<okhttp3.Response> bindMobileNumber(@org.jetbrains.annotations.NotNull()
    java.lang.String phone, @org.jetbrains.annotations.NotNull()
    java.lang.String code, @org.jetbrains.annotations.NotNull()
    java.lang.String id) {
        return null;
    }
    
    public RemoteUserRepository() {
        super();
    }
}