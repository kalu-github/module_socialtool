package lib.kalu.socialtool.wechat;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;

import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

import lib.kalu.socialtool.R;
import lib.kalu.socialtool.core.ISocialCallback;
import lib.kalu.socialtool.core.ISocialRequestBody;
import lib.kalu.socialtool.core.ISocialRequestClient;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @see <a href="https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=1417751808&token=&lang=zh_CN">Des</a>
 */
public class WechatRequestClientImpi implements ISocialRequestClient {

    private static WechatRequestClientImpi mWechatRequestClientImpi;
    private static ISocialCallback mISocialCallback;

    private IWXAPI mWXApi;
    private ISocialRequestBody mRequest;

    private WechatRequestClientImpi(Activity mActivity, String wxAppId) {
        mWXApi = WXAPIFactory.createWXAPI(mActivity.getApplicationContext(), wxAppId, false);
        mWXApi.registerApp(wxAppId);
    }

    public static WechatRequestClientImpi getInstance(Activity mActivity, String wxAppId) {
        if (mWechatRequestClientImpi == null) {
            synchronized (WechatRequestClientImpi.class) {
                if (mWechatRequestClientImpi == null) {
                    mWechatRequestClientImpi = new WechatRequestClientImpi(mActivity, wxAppId);
                }
            }
        }
        return mWechatRequestClientImpi;
    }

    public IWXAPI getWXApi() {
        return mWXApi;
    }

    public void onShareResp(int errCode, String errStr, String resultCode) {

        Log.d("wxabstractentryactivity", "onShareResp => errCode = " + errCode + ", errStr = " + errStr + ", resultCode = " + resultCode);

        if (mISocialCallback == null)
            return;

        // 成功
        if (errCode == BaseResp.ErrCode.ERR_OK) {
            mISocialCallback.onSucc(resultCode);
        }
        // 取消
        else if (errCode == BaseResp.ErrCode.ERR_USER_CANCEL) {
            mISocialCallback.onCancel();
        }
        // 失败
        else {

            if (!TextUtils.isEmpty(errStr)) {
                mISocialCallback.onFail(errStr);
            } else {
                mISocialCallback.onFail(String.valueOf(errCode));
            }
        }

        mISocialCallback = null;
    }

    public void onPayResp(int errCode, String errStr, String resultCode) {

        if (mISocialCallback == null)
            return;

        // 成功
        if (errCode == BaseResp.ErrCode.ERR_OK) {
            mISocialCallback.onSucc(resultCode);
        }
        // 取消
        else if (errCode == BaseResp.ErrCode.ERR_USER_CANCEL) {
            mISocialCallback.onCancel();
        }
        // 失败
        else {

            if (!TextUtils.isEmpty(errStr)) {
                mISocialCallback.onFail(errStr);
            } else {
                mISocialCallback.onFail(String.valueOf(errCode));
            }
        }

        mISocialCallback = null;
    }

    public void onLoginResp(int errCode, String errStr, String resultCode) {

        if (mISocialCallback == null)
            return;

        // 成功
        if (errCode == BaseResp.ErrCode.ERR_OK) {
            mISocialCallback.onSucc(resultCode);
        }
        // 取消
        else if (errCode == BaseResp.ErrCode.ERR_USER_CANCEL) {
            mISocialCallback.onCancel();
        }
        // 失败
        else {

            if (!TextUtils.isEmpty(errStr)) {
                mISocialCallback.onFail(errStr);
            } else {
                mISocialCallback.onFail(String.valueOf(errCode));
            }
        }

        mISocialCallback = null;
    }

    //检测是否支持微信支付
    private boolean isSupport() {
        return mWXApi.isWXAppInstalled() && mWXApi.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
    }

    @Override
    public void login(Activity activity, ISocialRequestBody request, ISocialCallback callback) {

        if (!(request instanceof WechatRequestBodyLoginImpli))
            return;

        this.mRequest = request;
        mISocialCallback = callback;

        boolean support = isSupport();

        if (support) {

            String reqScope = ((WechatRequestBodyLoginImpli) request).getReqScope();
            String reqState = ((WechatRequestBodyLoginImpli) request).getReqState();

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

            if (mISocialCallback != null) {
                String str = activity.getResources().getString(R.string.social_install_wechat);
                mISocialCallback.onFail(str);
            }
        }
    }

    @Override
    public void pay(Activity activity, ISocialRequestBody request, ISocialCallback callback) {

        if (!(request instanceof WechatRequestBodyPayImpli))
            return;

        this.mRequest = request;
        mISocialCallback = callback;

        boolean support = isSupport();

        if (support) {

            String appid = ((WechatRequestBodyPayImpli) request).getAppid();
            String partnerid = ((WechatRequestBodyPayImpli) request).getPartnerid();
            String prepayId = ((WechatRequestBodyPayImpli) request).getPrepayId();
            String packageValue = ((WechatRequestBodyPayImpli) request).getPackageValue();
            String nonceStr = ((WechatRequestBodyPayImpli) request).getNonceStr();
            String timestamp = ((WechatRequestBodyPayImpli) request).getTimestamp();
            String sign = ((WechatRequestBodyPayImpli) request).getSign();

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

            if (mISocialCallback != null) {
                String str = activity.getResources().getString(R.string.social_install_wechat);
                mISocialCallback.onFail(str);
            }
        }
    }

    @Override
    public void share(Activity activity, ISocialRequestBody request, ISocialCallback callback) {

        Log.d("wechatrequestclientimpi", "share => request = " + request + ", callback = " + callback);

        if (!(request instanceof WechatRequestBodyShareImpli))
            return;

        this.mRequest = request;
        mISocialCallback = callback;

        boolean support = isSupport();

        if (support) {

            int scene = ((WechatRequestBodyShareImpli) request).getmShareScene();
            String title = ((WechatRequestBodyShareImpli) request).getmShareTitle();
            String link = ((WechatRequestBodyShareImpli) request).getmShareLink();
            String description = ((WechatRequestBodyShareImpli) request).getmShareDescription();

            // 图片
            String thumbUrl = ((WechatRequestBodyShareImpli) request).getmShareThumbUrl();
            int thumbRaw = ((WechatRequestBodyShareImpli) request).getmShareThumbRaw();
            byte[] bytes1 = ((WechatRequestBodyShareImpli) request).getmShareImageByteDefault();
            byte[] bytes2 = ((WechatRequestBodyShareImpli) request).getmShareImageByteThumb();

            if (callback == null || scene < 0 || scene > 2 || (TextUtils.isEmpty(title) && TextUtils.isEmpty(description) && TextUtils.isEmpty(link))) {
                if (callback != null) {
                    String nulls = activity.getResources().getString(R.string.social_null_wechat);
                    callback.onFail(nulls);
                }
            } else {

                if (callback != null) {
                    callback.onStart();
                }

                if (null != bytes1 && null != bytes2) {

                    //初始化WXImageObject和WXMediaMessage对象
                    WXImageObject imageObject = new WXImageObject(bytes1);

                    WXMediaMessage msg = new WXMediaMessage();
                    msg.mediaObject = imageObject;
                    msg.thumbData = bytes2;

                    // 分享
                    SendMessageToWX.Req req = new SendMessageToWX.Req();
                    req.transaction = "webpage" + System.nanoTime();
                    req.message = msg;
                    req.scene = scene;
                    mWXApi.sendReq(req);

                } else {

                    WXWebpageObject webpageObject = new WXWebpageObject();
                    webpageObject.webpageUrl = link;

                    WXMediaMessage wxMediaMessage = new WXMediaMessage();
                    wxMediaMessage.mediaObject = webpageObject;

                    // 网络图片
                    byte[] bytesNet = null;

                    // 获取网络图片
                    try {

                        bytesNet = Executors.newSingleThreadExecutor().submit(new Callable<byte[]>() {
                            @Override
                            public byte[] call() {
                                try {

                                    Call call = new OkHttpClient().newCall(new Request.Builder().url(thumbUrl).build());
                                    Response response = call.execute();
                                    return response.body().bytes();

                                } catch (Exception e) {
                                    return null;
                                }
                            }
                        }).get();

                    } catch (Exception e) {
                    }

                    // 获取网络图片异常, 本地图片
                    if (null == bytesNet || bytesNet.length == 0) {

                        Log.d("WechatRequestClientImpi", "share => 获取网络图片异常, 本地图片");

                        Context context = activity.getApplicationContext();
                        Resources resources = context.getResources();
                        TypedValue value = new TypedValue();
                        resources.openRawResource(thumbRaw, value);

                        InputStream is = resources.openRawResource(thumbRaw);
                        Bitmap bitmap = BitmapFactory.decodeStream(is);

                        try {
                            is.close();
                        } catch (Exception e) {
                        }

                        wxMediaMessage.setThumbImage(bitmap);

                    }
                    // 32KB
                    else if (bytesNet.length <= 32 * 1024) {

                        // Toast.makeText(activity, "" + (bytesNet.length / 1024), Toast.LENGTH_SHORT).show();
                        Log.d("WechatRequestClientImpi", "share => size = " + bytesNet.length / 1024 + "KB" + ", url = " + thumbUrl);

                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytesNet, 0, bytesNet.length);
                        wxMediaMessage.setThumbImage(bitmap);

                    }
                    // 压缩策略
                    else {

                        // 压缩1
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inJustDecodeBounds = false;
                        options.inSampleSize = 4;
                        options.inPreferredConfig = Bitmap.Config.RGB_565;
                        Bitmap decodeByteArray = BitmapFactory.decodeByteArray(bytesNet, 0, bytesNet.length, options);

                        // 压缩2
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        decodeByteArray.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                        // 32KB
                        if (baos.toByteArray().length > 32 * 1024) {
                            baos.reset();
                            decodeByteArray.compress(Bitmap.CompressFormat.JPEG, 90, baos);
                        }

                        byte[] bytesLocal = baos.toByteArray();
                        if (null != decodeByteArray) {
                            decodeByteArray.recycle();
                        }

                        try {
                            baos.close();
                        } catch (Exception e) {
                        }

                        // 32KB
                        if (bytesLocal.length <= 32 * 1024) {

//                            BitmapRegionDecoder decoder = BitmapRegionDecoder.newInstance(bytes, 0, bytes.length, false);
//                            Bitmap bitmap = decoder.decodeRegion(new Rect(0, 0, 200, 200), options);
//                            wxMediaMessage.setThumbImage(bitmap);

//                             Toast.makeText(activity, "" + (bytesLocal.length / 1024), Toast.LENGTH_SHORT).show();
                            Log.d("WechatRequestClientImpi", "share => size = " + bytesLocal.length / 1024 + "KB" + ", url = " + thumbUrl);

                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytesLocal, 0, bytesLocal.length);
                            wxMediaMessage.setThumbImage(bitmap);

                        }
                        // 不压了, 用本地图片
                        else {

                            Log.d("WechatRequestClientImpi", "share => 不压了, 用本地图片");

                            Context context = activity.getApplicationContext();
                            Resources resources = context.getResources();
                            TypedValue value = new TypedValue();
                            resources.openRawResource(thumbRaw, value);

                            InputStream is = resources.openRawResource(thumbRaw);
                            Bitmap bitmap = BitmapFactory.decodeStream(is);

                            try {
                                is.close();
                            } catch (Exception e) {
                            }

                            wxMediaMessage.setThumbImage(bitmap);
                        }
                    }

                    // 标题信息
                    if (!TextUtils.isEmpty(title)) {
                        wxMediaMessage.title = title;
                    }

                    // 文字信息
                    if (!TextUtils.isEmpty(description)) {
                        wxMediaMessage.description = description;
                    }

                    // 分享
                    SendMessageToWX.Req req = new SendMessageToWX.Req();
                    req.transaction = "webpage" + System.nanoTime();
                    req.message = wxMediaMessage;
                    req.scene = scene;
                    mWXApi.sendReq(req);

                }
            }

        } else {

            if (mISocialCallback != null) {
                String str = activity.getResources().getString(R.string.social_install_wechat);
                mISocialCallback.onFail(str);
            }
        }
    }
}
