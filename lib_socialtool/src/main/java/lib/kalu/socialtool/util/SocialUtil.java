package lib.kalu.socialtool.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class SocialUtil {

    public static final boolean isInstallAlipayApp(PackageManager manager) {

        Uri uri = Uri.parse("alipays://platformapi/startApp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        ComponentName componentName = intent.resolveActivity(manager);

        return (null != componentName);
    }

    public static final boolean isInstallWechatApp(Context context) {

        IWXAPI mWXApi = WXAPIFactory.createWXAPI(context, "", false);
        boolean b = mWXApi.isWXAppInstalled() && mWXApi.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;

        mWXApi.unregisterApp();
        mWXApi.detach();
        mWXApi = null;
        return b;
    }
}
