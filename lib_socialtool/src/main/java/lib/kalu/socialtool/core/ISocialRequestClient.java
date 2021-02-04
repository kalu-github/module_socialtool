package lib.kalu.socialtool.core;

import android.app.Activity;

/**
 * 描述 ：统一支付接口。策略模式中统一算法接口。
 */
public interface ISocialRequestClient {

    /**
     * 登录
     *
     * @param activity
     * @param request
     * @param callback
     * @param <M>
     */
    <M extends ISocialRequestBody> void login(Activity activity, M request, ISocialCallback callback);

    /**
     * 支付
     *
     * @param activity
     * @param request
     * @param callback
     * @param <M>
     */
    <M extends ISocialRequestBody> void pay(Activity activity, M request, ISocialCallback callback);

    /**
     * 分享
     *
     * @param activity
     * @param request
     * @param callback
     * @param <M>
     */
    <M extends ISocialRequestBody> void share(Activity activity, M request, ISocialCallback callback);
}
