package lib.kalu.socialtool.media;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

import lib.kalu.socialtool.impl.ParamsImpl;
import lib.kalu.socialtool.impl.MediaImpl;
import lib.kalu.socialtool.params.WechatParamsShareImageToDefault;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * description: 构建 WXImageObject
 * created by kalu on 2021-02-08
 */
public final class WechatMediaImage implements MediaImpl {

    private WechatMediaImage() {
    }

    private @NonNull
    ParamsImpl paramsImpl;

    public WechatMediaImage(@NonNull ParamsImpl paramsImpl) {
        this.paramsImpl = paramsImpl;
    }

    @Override
    public WXMediaMessage create() {

        // 容错
        if (!(this.paramsImpl instanceof WechatParamsShareImageToDefault))
            return null;

        WechatParamsShareImageToDefault params = (WechatParamsShareImageToDefault) this.paramsImpl;
        byte[] imageData = params.getImageData();
        String imagePath = params.getImagePath();

        // 网络图片
        if (!TextUtils.isEmpty(imagePath) && imagePath.startsWith("http")) {
            return createWXMediaMessage(imagePath);
        }
        // 本地图片
        else if (null != imageData) {
            return createWXMediaMessage(imageData);
        }
        // 容错
        else {
            return null;
        }
    }

    public WXMediaMessage createWXMediaMessage(@NonNull byte[] bytes) {

        // 检查原图大小, 10M
        long max1 = 1055644;
        byte[] imageBytes = checkBytes(bytes, max1, false);

        // 检查缩略图, 32KB
        long max2 = 32 * 1024;
        byte[] thumbData = checkBytes(imageBytes, max2, true);

        // 原图
        WXImageObject wxImageObject = new WXImageObject();
        wxImageObject.imageData = imageBytes;

        // 缩略图
        WXMediaMessage wxMediaMessage = new WXMediaMessage();
        wxMediaMessage.mediaObject = wxImageObject;
        wxMediaMessage.thumbData = thumbData;

        return wxMediaMessage;
    }

    public WXMediaMessage createWXMediaMessage(@NonNull String imagePath) {

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

            // 检查原图大小, 10M
            long max1 = 1055644;
            byte[] imageBytes = checkBytes(bytes, max1, false);

            // 检查缩略图, 32KB
            long max2 = 32 * 1024;
            byte[] thumbData = checkBytes(imageBytes, max2, true);

            // 原图
            WXImageObject wxImageObject = new WXImageObject();
            wxImageObject.imageData = imageBytes;

            // 缩略图
            WXMediaMessage wxMediaMessage = new WXMediaMessage();
            wxMediaMessage.mediaObject = wxImageObject;
            wxMediaMessage.thumbData = thumbData;

            return wxMediaMessage;

        } catch (Exception e) {

            return null;
        }
    }
}
