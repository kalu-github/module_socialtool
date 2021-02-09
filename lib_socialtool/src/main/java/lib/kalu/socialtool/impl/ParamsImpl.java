package lib.kalu.socialtool.impl;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;

import java.io.Serializable;

/**
 * description: 发起参数Impl
 * created by kalu on 2021-02-09
 */
public interface ParamsImpl extends Serializable {

    /**
     * 分享内容类型
     *
     * @return
     */
    int contentType();

    /**
     * 分享平台类型
     *
     * @return
     */
    int platfromType();


    /**
     * 一、文字类型分享示例
     * <p>
     * WXTextObject （WXMediaMessage.IMediaObject 的派生类，用于描述一个文本对象）
     */
    int CONTENT_WECHAT_TEXT = 1;

    /**
     * 二、图片类型分享示例
     * <p>
     * WXImageObject （WXMediaMessage.IMediaObject 的派生类，用于描述一个图片对象）
     */
    int CONTENT_WECHAT_IMAGE = 2;

    /**
     * 三、音乐类型分享示例
     * <p>
     * WXMusicObject（WXMediaMessage.IMediaObject 的派生类，用于描述一个音频对象）
     */
    int CONTENT_WECHAT_MUSIC = 3;

    /**
     * 四、视频类型分享示例
     * <p>
     * WXVideoObject （WXMediaMessage.IMediaObject 的派生类，用于描述一个视频对象）
     */
    int CONTENT_WECHAT_VIDEO = 4;

    /**
     * 五、网页类型分享示例
     * <p>
     * WXWebpageObject （WXMediaMessage.IMediaObject 的派生类，用于描述一个网页对象）
     */
    int CONTENT_WECHAT_WEBPAGE = 5;

    /**
     * 六、小程序类型分享示例
     * <p>
     * WXMiniProgramObject （WXMediaMessage.IMediaObject 的派生类，用于描述一个小程序对象）
     */
    int CONTENT_WECHAT_MINIPROGRAM = 6;

    /**
     * 分享到对话:
     * endMessageToWX.Req.WXSceneSession
     */
    int PLATFROM_WECHAT_WXSCENESESSION = SendMessageToWX.Req.WXSceneSession;

    /**
     * 分享到朋友圈:
     * endMessageToWX.Req.WXSceneTimeline
     */
    int PLATFROM_WECHAT_WXSCENETIMELINE = SendMessageToWX.Req.WXSceneTimeline;

    /**
     * 分享到收藏:
     * endMessageToWX.Req.WXSceneFavorite
     */
    int PLATFROM_WECHAT_WXSCENEFAVORITE = SendMessageToWX.Req.WXSceneFavorite;
}
