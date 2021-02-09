package lib.kalu.socialtool.params;

import androidx.annotation.Keep;

import java.io.Serializable;

import lib.kalu.socialtool.impl.ParamsImpl;

/**
 * description: 发起参数 - 微信 - 网页 - 分享到对话
 * created by kalu on 2021-02-09
 */
@Keep
public class WechatParamsShareWebpageToDefault implements Serializable, ParamsImpl {

    private String title = null;
    private String description = null;
    private String webpageUrl = null;

    private String thumbPath = null;
    private byte[] thumbData = null;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebpageUrl() {
        return webpageUrl;
    }

    public void setWebpageUrl(String webpageUrl) {
        this.webpageUrl = webpageUrl;
    }

    public String getThumbPath() {
        return thumbPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    public byte[] getThumbData() {
        return thumbData;
    }

    public void setThumbData(byte[] thumbData) {
        this.thumbData = thumbData;
    }

    @Override
    public int contentType() {
        return CONTENT_WECHAT_WEBPAGE;
    }

    @Override
    public int platfromType() {
        return PLATFROM_WECHAT_WXSCENESESSION;
    }
}
