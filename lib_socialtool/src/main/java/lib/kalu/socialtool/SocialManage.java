package lib.kalu.socialtool;

import android.app.Activity;

import androidx.annotation.Keep;

import lib.kalu.socialtool.impl.WechatClientImpl;
import lib.kalu.socialtool.impl.WechatParamsImpl;
import lib.kalu.socialtool.listener.OnSocialChangeListener;

/**
 * 描述 ：策略模式场景类。
 * 调用 : 实例化支付策略payway,以及支付订单信息，作为参数直接传入。
 * 使用方法1：调用EasyPay.pay()方法即可。
 * 使用方法2：实例化payStrategy,直接调用其pay方法。如：new Alipay().pay(...)
 */
@Keep
public class SocialManage {

    public static <T extends WechatClientImpl, M extends WechatParamsImpl> void login(Activity mActivity, T client, M params, OnSocialChangeListener callback) {
        client.login(mActivity, params, callback);
    }

    public static <T extends WechatClientImpl, M extends WechatParamsImpl> void pay(Activity mActivity, T client, M params, OnSocialChangeListener callback) {
        client.pay(mActivity, params, callback);
    }

    public static <T extends WechatClientImpl, M extends WechatParamsImpl> void share(Activity mActivity, T client, M params, OnSocialChangeListener callback) {
        client.share(mActivity, params, callback);
    }
}
