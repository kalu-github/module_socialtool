package lib.kalu.socialtool.impl;

import android.app.Activity;

import java.io.Serializable;

import lib.kalu.socialtool.listener.OnSocialChangeListener;

/**
 * 描述 ：统一支付接口。策略模式中统一算法接口。
 */
public interface WechatClientImpl {

    /**
     * 登录
     *
     * @param activity
     * @param request
     * @param callback
     * @param <M>
     */
    <M extends ParamsImpl & Serializable> void login(Activity activity, M request, OnSocialChangeListener callback);

    /**
     * 支付
     *
     * @param activity
     * @param request
     * @param callback
     * @param <M>
     */
    <M extends ParamsImpl & Serializable> void pay(Activity activity, M request, OnSocialChangeListener callback);

    /**
     * 分享
     *
     * @param activity
     * @param request
     * @param callback
     * @param <M>
     */
    <M extends ParamsImpl & Serializable> void share(Activity activity, M request, OnSocialChangeListener callback);
}
