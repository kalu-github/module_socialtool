package lib.kalu.socialtool.ui.wechat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Keep;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import lib.kalu.socialtool.wechat.WechatRequestClientImpi;

/**
 * description: 微信支付回调 WXAbstractEntryActivity
 * create by kalu on 2019/10/16
 */
@Keep
public abstract class WXAbstractEntryActivity extends Activity implements IWXAPIEventHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WechatRequestClientImpi.getInstance(this, "").getWXApi().handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        WechatRequestClientImpi.getInstance(this, "").getWXApi().handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        Log.d("wxabstractentryactivity", "onReq =>");
    }

    @Override
    public void onResp(BaseResp baseResp) {

        int type = baseResp.getType();
        Log.d("wxabstractentryactivity", "onResp => type = " + type);

        switch (type) {
            // 登录
            case ConstantsAPI.COMMAND_SENDAUTH:

                String resultCodeL = "";
                int errorCodeL = baseResp.errCode;
                String errStrL = baseResp.errStr;

                try {
                    resultCodeL = ((SendAuth.Resp) baseResp).code;
                } catch (Exception e) {
                    resultCodeL = "";
                }

                WechatRequestClientImpi.getInstance(this, "").onLoginResp(errorCodeL, errStrL, resultCodeL);

                break;
            // 支付
            case ConstantsAPI.COMMAND_PAY_BY_WX:

                String resultCodeP = "";
                int errorCodeP = baseResp.errCode;
                String errStrP = baseResp.errStr;

                try {
                    resultCodeP = ((SendAuth.Resp) baseResp).code;
                } catch (Exception e) {
                    resultCodeP = "";
                }

                WechatRequestClientImpi.getInstance(this, "").onPayResp(errorCodeP, errStrP, resultCodeP);

                break;
            // 分享
            case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX:

                String resultCodeS = "";
                int errorCodeS = baseResp.errCode;
                String errStrS = baseResp.errStr;

                try {
                    resultCodeS = ((SendAuth.Resp) baseResp).code;
                } catch (Exception e) {
                    resultCodeS = "";
                }

                WechatRequestClientImpi.getInstance(this, "").onShareResp(errorCodeS, errStrS, resultCodeS);
                break;
            // other
            default:

                break;
        }

        finish();
    }
}