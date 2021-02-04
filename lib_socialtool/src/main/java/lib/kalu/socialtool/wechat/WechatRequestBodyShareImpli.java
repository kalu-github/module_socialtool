package lib.kalu.socialtool.wechat;

import androidx.annotation.RawRes;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;

import lib.kalu.socialtool.core.ISocialRequestBody;

/**
 * description: 微信分享
 * create by Administrator on 2020-05-28
 */
public class WechatRequestBodyShareImpli implements ISocialRequestBody {

    // 分享类型
    // SendMessageToWX.Req.WXSceneSession是分享到好友会话
    // SendMessageToWX.Req.WXSceneTimeline是分享到朋友圈
    private int mShareScene = SendMessageToWX.Req.WXSceneTimeline;

    // 分享标题
    private String mShareTitle = "";
    // 分享文字
    private String mShareDescription = "";
    // 链接
    private String mShareLink = "";
    // 缩略图：网络图片
    private String mShareThumbUrl = null;
    // 缩略图：Raw文件夹下本地图片
    private @RawRes
    int mShareThumbRaw = Integer.MIN_VALUE;

    // 分享纯图片
    private byte[] mShareImageByteDefault = null;
    private byte[] mShareImageByteThumb = null;

    public int getmShareScene() {
        return mShareScene;
    }

    public void setmShareScene(int mShareScene) {
        this.mShareScene = mShareScene;
    }

    public String getmShareTitle() {
        return mShareTitle;
    }

    public void setmShareTitle(String mShareTitle) {
        this.mShareTitle = mShareTitle;
    }

    public String getmShareDescription() {
        return mShareDescription;
    }

    public void setmShareDescription(String mShareDescription) {
        this.mShareDescription = mShareDescription;
    }

    public String getmShareThumbUrl() {
        return mShareThumbUrl;
    }

    public void setmShareThumbUrl(String mShareThumbUrl) {
        this.mShareThumbUrl = mShareThumbUrl;
    }

    public int getmShareThumbRaw() {
        return mShareThumbRaw;
    }

    public void setmShareThumbRaw(int mShareThumbRaw) {
        this.mShareThumbRaw = mShareThumbRaw;
    }

    public String getmShareLink() {
        return mShareLink;
    }

    public void setmShareLink(String mShareLink) {
        this.mShareLink = mShareLink;
    }

    public byte[] getmShareImageByteDefault() {
        return mShareImageByteDefault;
    }

    public void setmShareImageByteDefault(byte[] mShareImageByteDefault) {
        this.mShareImageByteDefault = mShareImageByteDefault;
    }

    public byte[] getmShareImageByteThumb() {
        return mShareImageByteThumb;
    }

    public void setmShareImageByteThumb(byte[] mShareImageByteThumb) {
        this.mShareImageByteThumb = mShareImageByteThumb;
    }
}
