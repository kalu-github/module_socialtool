package lib.kalu.socialtool.impl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

import androidx.annotation.NonNull;

import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;

import java.io.ByteArrayOutputStream;

/**
 * description:
 * created by kalu on 2021-02-09
 */
public interface MediaImpl {

    WXMediaMessage create();

    default byte[] checkBytes(@NonNull byte[] bytes, long max, boolean isThumb) {

        Log.d("kalu", "MediaImpl => ---------------------------------------");
        Log.d("kalu", "MediaImpl => checkBytes => start");
        Log.d("kalu", "MediaImpl => checkBytes => isThumb = " + isThumb + ", max = " + max + ", size = " + bytes.length / 1024 + "Kb");

        // max
        if (bytes.length <= max) {
            Log.d("kalu", "MediaImpl => checkBytes => 符合条件");
            Log.d("kalu", "MediaImpl => ---------------------------------------");
            return bytes;
        }
        // scale
        else {
            Log.d("kalu", "MediaImpl => checkBytes => 压缩图片");
            Log.d("kalu", "MediaImpl => ---------------------------------------");
            return scaleBytes(bytes, max, isThumb);
        }
    }

    default byte[] scaleBytes(@NonNull byte[] bytes, long max, boolean isThumb) {

        Log.d("kalu", "MediaImpl => ---------------------------------------");
        Log.d("kalu", "MediaImpl => scaleBytes => start");
        Log.d("kalu", "MediaImpl => scaleBytes =>  isThumb = " + isThumb);
        Log.d("kalu", "MediaImpl => scaleBytes =>  max = " + max);
        Log.d("kalu", "MediaImpl => scaleBytes =>  bytes = " + bytes);
        Log.d("kalu", "MediaImpl => scaleBytes =>  size1 = " + bytes.length / 1024 + "KB");

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;

        double sqrt = Math.sqrt(bytes.length / max);
        Log.d("kalu", "MediaImpl => scaleBytes =>  sqrt = " + sqrt);
        if (sqrt >= 4f) {
            options.inSampleSize = isThumb ? 8 : 4;
        } else if (sqrt >= 2f) {
            options.inSampleSize = isThumb ? 4 : 2;
        } else {
            options.inSampleSize = isThumb ? 2 : 1;
        }

        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);

        int realWidth = bitmap.getWidth();
        int realHeight = bitmap.getHeight();

        float outWidth;
        if (sqrt >= 4f) {
            outWidth = realWidth * (isThumb ? 0.125f : 0.225f);
        } else if (sqrt >= 2f) {
            outWidth = realWidth * (isThumb ? 0.25f : 0.45f);
        } else {
            outWidth = realWidth * (isThumb ? 0.5f : 0.9f);
        }
        float outHeight = outWidth * realHeight / realWidth;

        float scaleWidth = outWidth / realWidth;
        float scaleHeight = outHeight / realHeight;

        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        // 得到新的图片
        Bitmap outBitMap = Bitmap.createBitmap(bitmap, 0, 0, realWidth, realHeight, matrix, true);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        outBitMap.compress(isThumb ? Bitmap.CompressFormat.JPEG : Bitmap.CompressFormat.PNG, isThumb ? 40 : 100, baos);
        byte[] scaleBytes = baos.toByteArray();

        if (null != bitmap) {
            bitmap.recycle();
        }

        if (null != outBitMap) {
            outBitMap.recycle();
        }

        if (null != bytes) {
            bytes = null;
        }

        Log.d("kalu", "MediaImpl => scaleBytes =>  size2 = " + scaleBytes.length / 1024 + "KB");

        // 符合条件
        if (scaleBytes.length <= max) {

            Log.d("kalu", "MediaImpl => scaleBytes =>  符合条件");
            Log.d("kalu", "MediaImpl => ---------------------------------------");

            return scaleBytes;
        }
        // 再次压缩
        else {

            Log.d("kalu", "MediaImpl => scaleBytes =>  再次压缩");
            Log.d("kalu", "MediaImpl => ---------------------------------------");

            return scaleBytes(scaleBytes, max, isThumb);
        }
    }
}
