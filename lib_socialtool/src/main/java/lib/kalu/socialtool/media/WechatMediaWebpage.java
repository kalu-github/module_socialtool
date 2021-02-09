package lib.kalu.socialtool.media;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

import lib.kalu.socialtool.impl.MediaImpl;
import lib.kalu.socialtool.impl.ParamsImpl;
import lib.kalu.socialtool.params.WechatParamsShareWebpageToDefault;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * description: 构建 WXImageObject
 * created by kalu on 2021-02-08
 */
public final class WechatMediaWebpage implements MediaImpl {

    private WechatMediaWebpage() {
    }

    private @NonNull
    ParamsImpl paramsImpl;

    public WechatMediaWebpage(@NonNull ParamsImpl paramsImpl) {
        this.paramsImpl = paramsImpl;
    }

    @Override
    public WXMediaMessage create() {

        // 容错
        if (!(this.paramsImpl instanceof WechatParamsShareWebpageToDefault))
            return null;

        WechatParamsShareWebpageToDefault params = (WechatParamsShareWebpageToDefault) this.paramsImpl;
        byte[] thumbData = params.getThumbData();
        String thumbPath = params.getThumbPath();
        String title = params.getTitle();
        String description = params.getDescription();
        String webpageUrl = params.getWebpageUrl();

        // 网络图片
        if (!TextUtils.isEmpty(thumbPath) && thumbPath.startsWith("http") && !TextUtils.isEmpty(webpageUrl) && webpageUrl.startsWith("http") && !TextUtils.isEmpty(title) && !TextUtils.isEmpty(description)) {
            return createWXMediaMessage(thumbPath, title, description, webpageUrl);
        }
        // 本地图片
        if (null != thumbData && !TextUtils.isEmpty(webpageUrl) && webpageUrl.startsWith("http") && !TextUtils.isEmpty(title) && !TextUtils.isEmpty(description)) {
            return createWXMediaMessage(thumbData, title, description, webpageUrl);
        }
        // 容错
        else {
            return null;
        }
    }

    public WXMediaMessage createWXMediaMessage(@NonNull byte[] bytes, @NonNull String title, @NonNull String description, @NonNull String webpageUrl) {

        // 检查缩略图, 32KB
        long max2 = 32 * 1024;
        byte[] thumbData = checkBytes(bytes, max2, true);

        // 初始化一个WXWebpageObject，填写url
        WXWebpageObject wxWebpageObject = new WXWebpageObject();
        wxWebpageObject.webpageUrl = webpageUrl;

        // 缩略图
        WXMediaMessage wxMediaMessage = new WXMediaMessage();
        wxMediaMessage.title = title;
        wxMediaMessage.description = description;
        wxMediaMessage.mediaObject = wxWebpageObject;
        wxMediaMessage.thumbData = thumbData;

        return wxMediaMessage;
    }

    public WXMediaMessage createWXMediaMessage(@NonNull String imagePath, @NonNull String title, @NonNull String description, @NonNull String webpageUrl) {

        try {

            byte[] bytes = Executors.newSingleThreadExecutor().submit(new Callable<byte[]>() {
                @Override
                public byte[] call() {
                    try {

                        Call call = new OkHttpClient().newCall(new Request.Builder().url(imagePath).build());
                        Response response = call.execute();
                        return response.body().bytes();

                    } catch (Exception e) {
                        return null;
                    }
                }
            }).get();

            // 检查缩略图, 32KB
            long max2 = 32 * 1024;
            byte[] thumbData = checkBytes(bytes, max2, true);

            // 初始化一个WXWebpageObject，填写url
            WXWebpageObject wxWebpageObject = new WXWebpageObject();
            wxWebpageObject.webpageUrl = webpageUrl;

            // 缩略图
            WXMediaMessage wxMediaMessage = new WXMediaMessage();
            wxMediaMessage.title = title;
            wxMediaMessage.description = description;
            wxMediaMessage.mediaObject = wxWebpageObject;
            wxMediaMessage.thumbData = thumbData;

            return wxMediaMessage;

        } catch (Exception e) {

            return null;
        }
    }
}
