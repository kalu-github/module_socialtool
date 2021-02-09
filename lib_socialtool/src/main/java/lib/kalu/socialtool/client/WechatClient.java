package lib.kalu.socialtool.client;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Keep;

import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import lib.kalu.socialtool.R;
import lib.kalu.socialtool.callback.RespCallback;
import lib.kalu.socialtool.impl.ParamsImpl;
import lib.kalu.socialtool.impl.WechatClientImpl;
import lib.kalu.socialtool.listener.OnSocialChangeListener;
import lib.kalu.socialtool.impl.MediaImpl;
import lib.kalu.socialtool.media.WechatMediaImage;
import lib.kalu.socialtool.media.WechatMediaWebpage;
import lib.kalu.socialtool.params.WechatParamsLogin;
import lib.kalu.socialtool.params.WechatParamsPay;

/**
 * @see <a href="https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=1417751808&token=&lang=zh_CN">Des</a>
 */
@Keep
public class WechatClient implements WechatClientImpl, RespCallback {

    private static WechatClient mWechatRequestClientImpi;
    private static OnSocialChangeListener mOnSocialChangeListener;

    private IWXAPI mWXApi;
//    private ParamsImpl mRequest;

    private WechatClient(Activity mActivity, String wxAppId) {
        mWXApi = WXAPIFactory.createWXAPI(mActivity.getApplicationContext(), wxAppId, false);
        mWXApi.registerApp(wxAppId);
    }

    public static WechatClient getInstance(Activity mActivity, String wxAppId) {
        if (mWechatRequestClientImpi == null) {
            synchronized (WechatClient.class) {
                if (mWechatRequestClientImpi == null) {
                    mWechatRequestClientImpi = new WechatClient(mActivity, wxAppId);
                }
            }
        }
        return mWechatRequestClientImpi;
    }

    public IWXAPI getWXApi() {
        return mWXApi;
    }


    //检测是否支持微信支付
    private boolean isSupport() {
        return mWXApi.isWXAppInstalled() && mWXApi.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
    }

    @Override
    public void login(Activity activity, ParamsImpl request, OnSocialChangeListener callback) {

        if (!(request instanceof WechatParamsLogin))
            return;

//        this.mRequest = request;
        mOnSocialChangeListener = callback;

        boolean support = isSupport();

        if (support) {

            String reqScope = ((WechatParamsLogin) request).getReqScope();
            String reqState = ((WechatParamsLogin) request).getReqState();

            if (request == null || TextUtils.isEmpty(reqScope) || TextUtils.isEmpty(reqState)) {
                if (callback != null) {
                    String nulls = activity.getResources().getString(R.string.social_null_wechat);
                    callback.onFail(nulls);
                }
            } else {

                if (callback != null) {
                    callback.onStart();
                }

                SendAuth.Req req = new SendAuth.Req();
                req.scope = reqScope;
                req.state = reqState;
                mWXApi.sendReq(req);
            }

        } else {

            if (mOnSocialChangeListener != null) {
                String str = activity.getResources().getString(R.string.social_install_wechat);
                mOnSocialChangeListener.onFail(str);
            }
        }
    }

    @Override
    public void pay(Activity activity, ParamsImpl request, OnSocialChangeListener callback) {

        if (!(request instanceof WechatParamsPay))
            return;

//        this.mRequest = request;
        mOnSocialChangeListener = callback;

        boolean support = isSupport();

        if (support) {

            String appid = ((WechatParamsPay) request).getAppid();
            String partnerid = ((WechatParamsPay) request).getPartnerid();
            String prepayId = ((WechatParamsPay) request).getPrepayId();
            String packageValue = ((WechatParamsPay) request).getPackageValue();
            String nonceStr = ((WechatParamsPay) request).getNonceStr();
            String timestamp = ((WechatParamsPay) request).getTimestamp();
            String sign = ((WechatParamsPay) request).getSign();

            if (callback == null || TextUtils.isEmpty(appid) || TextUtils.isEmpty(partnerid)
                    || TextUtils.isEmpty(prepayId) || TextUtils.isEmpty(packageValue) ||
                    TextUtils.isEmpty(nonceStr) || TextUtils.isEmpty(timestamp) ||
                    TextUtils.isEmpty(sign)) {
                if (callback != null) {
                    String nulls = activity.getResources().getString(R.string.social_null_wechat);
                    callback.onFail(nulls);
                }
            } else {

                if (callback != null) {
                    callback.onStart();
                }

                PayReq req = new PayReq();
                req.appId = appid;
                req.partnerId = partnerid;
                req.prepayId = prepayId;
                req.packageValue = packageValue;
                req.nonceStr = nonceStr;
                req.timeStamp = timestamp;
                req.sign = sign;

                mWXApi.sendReq(req);
            }

        } else {

            if (mOnSocialChangeListener != null) {
                String str = activity.getResources().getString(R.string.social_install_wechat);
                mOnSocialChangeListener.onFail(str);
            }
        }
    }

    @Override
    public void share(Activity activity, ParamsImpl request, OnSocialChangeListener callback) {

        Log.d("wechatrequestclientimpi", "share => request = " + request + ", callback = " + callback);

        this.mOnSocialChangeListener = callback;

        if (null != this.mOnSocialChangeListener) {
            this.mOnSocialChangeListener.onStart();
        }

        // 非微信平台
        if (request.platfromType() != ParamsImpl.PLATFROM_WECHAT_WXSCENESESSION && request.platfromType() != ParamsImpl.PLATFROM_WECHAT_WXSCENETIMELINE && request.platfromType() != ParamsImpl.PLATFROM_WECHAT_WXSCENEFAVORITE) {

            if (mOnSocialChangeListener != null) {
                String str = activity.getResources().getString(R.string.social_install_wechat);
                mOnSocialChangeListener.onFail(str);
            }
            return;
        }

        // 未安装微信
        boolean support = isSupport();
        if (!support) {
            if (mOnSocialChangeListener != null) {
                String str = activity.getResources().getString(R.string.social_install_wechat);
                mOnSocialChangeListener.onFail(str);
            }
            return;
        }

        MediaImpl wechatMedia;
        //  图片类型分享示例, WXImageObject （WXMediaMessage.IMediaObject 的派生类，用于描述一个图片对象）
        if (ParamsImpl.CONTENT_WECHAT_IMAGE == request.contentType()) {
            wechatMedia = new WechatMediaImage(request);
        }
        // 网页类型分享示例, WXWebpageObject （WXMediaMessage.IMediaObject 的派生类，用于描述一个网页对象）
        else if (ParamsImpl.CONTENT_WECHAT_WEBPAGE == request.contentType()) {
            wechatMedia = new WechatMediaWebpage(request);
        }
        // 容错
        else {
            wechatMedia = null;
        }

        // 参数错误
        if (null == wechatMedia) {

            if (null != callback) {
                String nulls = activity.getResources().getString(R.string.social_null_wechat);
                callback.onFail(nulls);
            }
        }
        // 唤醒sdk
        else {
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = "webpage" + System.nanoTime();
            req.message = wechatMedia.create();
            req.scene = request.platfromType();
            mWXApi.sendReq(req);
        }
    }

    @Override
    public void onResp(int callbackType, int errCode, String errStr, String resultCode) {

        if (this.mOnSocialChangeListener == null)
            return;

        // login
        if (callbackType == SDK_CALLBACK_TYPE_LOGIN) {

            // 成功
            if (errCode == BaseResp.ErrCode.ERR_OK) {
                mOnSocialChangeListener.onSucc(resultCode);
            }
            // 取消
            else if (errCode == BaseResp.ErrCode.ERR_USER_CANCEL) {
                mOnSocialChangeListener.onCancel();
            }
            // 失败
            else {

                if (!TextUtils.isEmpty(errStr)) {
                    mOnSocialChangeListener.onFail(errStr);
                } else {
                    mOnSocialChangeListener.onFail(String.valueOf(errCode));
                }
            }

        }
        // share
        else if (callbackType == SDK_CALLBACK_TYPE_SHARE) {

            // 成功
            if (errCode == BaseResp.ErrCode.ERR_OK) {
                mOnSocialChangeListener.onSucc(resultCode);
            }
            // 取消
            else if (errCode == BaseResp.ErrCode.ERR_USER_CANCEL) {
                mOnSocialChangeListener.onCancel();
            }
            // 失败
            else {

                if (!TextUtils.isEmpty(errStr)) {
                    mOnSocialChangeListener.onFail(errStr);
                } else {
                    mOnSocialChangeListener.onFail(String.valueOf(errCode));
                }
            }
        }
        // paymney
        else if (callbackType == SDK_CALLBACK_TYPE_PAYMENT) {

            // 成功
            if (errCode == BaseResp.ErrCode.ERR_OK) {
                mOnSocialChangeListener.onSucc(resultCode);
            }
            // 取消
            else if (errCode == BaseResp.ErrCode.ERR_USER_CANCEL) {
                mOnSocialChangeListener.onCancel();
            }
            // 失败
            else {

                if (!TextUtils.isEmpty(errStr)) {
                    mOnSocialChangeListener.onFail(errStr);
                } else {
                    mOnSocialChangeListener.onFail(String.valueOf(errCode));
                }
            }
        }

        mOnSocialChangeListener = null;

    }
}
