package lib.kalu.socialtool.core;

import android.app.Activity;

/**
 * 描述 ：策略模式场景类。
 * 调用 : 实例化支付策略payway,以及支付订单信息，作为参数直接传入。
 * 使用方法1：调用EasyPay.pay()方法即可。
 * 使用方法2：实例化payStrategy,直接调用其pay方法。如：new Alipay().pay(...)
 */
public class SocialCore {

    public static <T extends ISocialRequestClient, M extends ISocialRequestBody> void login(Activity mActivity, T payRequestClient, M payRequestBody, ISocialCallback callback) {
        payRequestClient.login(mActivity, payRequestBody, callback);
    }

    public static <T extends ISocialRequestClient, M extends ISocialRequestBody> void pay(Activity mActivity, T payRequestClient, M payRequestBody, ISocialCallback callback) {
        payRequestClient.pay(mActivity, payRequestBody, callback);
    }

    public static <T extends ISocialRequestClient, M extends ISocialRequestBody> void share(Activity mActivity, T payRequestClient, M payRequestBody, ISocialCallback callback) {
        payRequestClient.share(mActivity, payRequestBody, callback);
    }
}
