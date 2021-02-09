package lib.kalu.socialtool.params;

import androidx.annotation.Keep;

import java.io.Serializable;

import lib.kalu.socialtool.impl.ParamsImpl;

/**
 * description: 发起参数 - 微信 - 分享到对话
 * created by kalu on 2021-02-09
 */
@Keep
public class WechatParamsShareImageToDefault implements Serializable, ParamsImpl {

    private byte[] imageData = null;
    private String imagePath = null;

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public int contentType() {
        return CONTENT_WECHAT_IMAGE;
    }

    @Override
    public int platfromType() {
        return PLATFROM_WECHAT_WXSCENESESSION;
    }
}
