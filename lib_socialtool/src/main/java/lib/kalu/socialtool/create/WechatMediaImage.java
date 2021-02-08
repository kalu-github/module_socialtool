package lib.kalu.socialtool.create;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

import lib.kalu.socialtool.params.WechatParamsShareImage;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * description: 构建 WXImageObject
 * created by kalu on 2021-02-08
 */
public final class WechatMediaImage implements WechatMedia {

    private WechatMediaImage() {
    }

    private @NonNull
    WechatParamsShareImage wechatParamsShareImage;

    public WechatMediaImage(@NonNull WechatParamsShareImage wechatParamsShareImage) {
        this.wechatParamsShareImage = wechatParamsShareImage;
    }

    @Override
    public WXMediaMessage create() {

        // 容错
        if (!(wechatParamsShareImage instanceof WechatParamsShareImage))
            return null;

        WechatParamsShareImage params = (WechatParamsShareImage) wechatParamsShareImage;
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

    private byte[] checkBytes(@NonNull byte[] bytes, long max, boolean isThumb) {

        Log.d("kalu", "---------------------------------------");
        Log.d("kalu", "WechatMediaImage => checkBytes => start");
        Log.d("kalu", "WechatMediaImage => checkBytes => isThumb = " + isThumb + ", max = " + max + ", size = " + bytes.length / 1024 + "Kb");

        // max
        if (bytes.length <= max) {
            Log.d("kalu", "WechatMediaImage => checkBytes => 符合条件");
            Log.d("kalu", "---------------------------------------");
            return bytes;
        }
        // scale
        else {
            Log.d("kalu", "WechatMediaImage => checkBytes => 压缩图片");
            Log.d("kalu", "---------------------------------------");
            return scaleBytes(bytes, max, isThumb);
        }
    }

    private byte[] scaleBytes(@NonNull byte[] bytes, long max, boolean isThumb) {

        Log.d("kalu", "---------------------------------------");
        Log.d("kalu", "WechatMediaImage => scaleBytes => start");
        Log.d("kalu", "WechatMediaImage => scaleBytes => isThumb = " + isThumb + ", max = " + max + ", size = " + bytes.length / 1024 + "Kb");

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inSampleSize = isThumb ? 2 : 1;

        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);

        int realWidth = bitmap.getWidth();
        int realHeight = bitmap.getHeight();

        float outWidth = realWidth * (isThumb ? 0.5f : 0.9f);
        float outHeight = outWidth * realHeight / realWidth;

        float scaleWidth = outWidth / realWidth;
        float scaleHeight = outHeight / realHeight;

        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        // 得到新的图片
        Bitmap outBitMap = Bitmap.createBitmap(bitmap, 0, 0, realWidth, realHeight, matrix, true);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        outBitMap.compress(Bitmap.CompressFormat.JPEG, isThumb ? 80 : 100, baos);
        byte[] scaleBytes = baos.toByteArray();

        if (null != bitmap) {
            bitmap.recycle();
        }

        if (null != outBitMap) {
            outBitMap.recycle();
        }

        Log.d("kalu", "WechatMediaImage => scaleBytes =>  size = " + scaleBytes.length / 1024 + "KB");

        // 符合条件
        if (scaleBytes.length <= max) {

            Log.d("kalu", "WechatMediaImage => scaleBytes =>  符合条件");
            Log.d("kalu", "---------------------------------------");

            return scaleBytes;
        }
        // 再次压缩
        else {

            Log.d("kalu", "WechatMediaImage => scaleBytes =>  再次压缩");
            Log.d("kalu", "---------------------------------------");

            return scaleBytes(scaleBytes, max, isThumb);
        }
    }
}
