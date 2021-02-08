package lib.kalu.socialtool.params;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;

import java.io.Serializable;

import lib.kalu.socialtool.impl.WechatParamsImpl;

/**
 * description: 微信分享
 * create by Administrator on 2020-05-28
 * https://developers.weixin.qq.com/doc/oplatform/Mobile_App/Share_and_Favorites/Android.html
 */
public interface WechatParamsShare extends WechatParamsImpl, Serializable {

    /**
     * 一、文字类型分享示例
     * <p>
     * WXTextObject （WXMediaMessage.IMediaObject 的派生类，用于描述一个文本对象）
     */
    int CONTENT_ONLY_TEXT = 1;

    /**
     * 二、图片类型分享示例
     * <p>
     * WXImageObject （WXMediaMessage.IMediaObject 的派生类，用于描述一个图片对象）
     */
    int CONTENT_ONLY_IMAGE = 2;

    /**
     * 三、音乐类型分享示例
     * <p>
     * WXMusicObject（WXMediaMessage.IMediaObject 的派生类，用于描述一个音频对象）
     */
    int CONTENT_ONLY_MUSIC = 3;

    /**
     * 四、视频类型分享示例
     * <p>
     * WXVideoObject （WXMediaMessage.IMediaObject 的派生类，用于描述一个视频对象）
     */
    int CONTENT_ONLY_VIDEO = 4;

    /**
     * 五、网页类型分享示例
     * <p>
     * WXWebpageObject （WXMediaMessage.IMediaObject 的派生类，用于描述一个网页对象）
     */
    int CONTENT_ONLY_WEBPAGE = 5;

    /**
     * 六、小程序类型分享示例
     * <p>
     * WXMiniProgramObject （WXMediaMessage.IMediaObject 的派生类，用于描述一个小程序对象）
     */
    int CONTENT_ONLY_MINIPROGRAM = 6;

    /**
     * 分享到对话:
     * endMessageToWX.Req.WXSceneSession
     */
    int SCENE_ONLY_WXSCENESESSION = SendMessageToWX.Req.WXSceneSession;

    /**
     * 分享到朋友圈:
     * endMessageToWX.Req.WXSceneTimeline
     */
    int SCENE_ONLY_WXSCENETIMELINE = SendMessageToWX.Req.WXSceneTimeline;

    /**
     * 分享到收藏:
     * endMessageToWX.Req.WXSceneFavorite
     */
    int SCENE_ONLY_WXSCENEFAVORITE = SendMessageToWX.Req.WXSceneFavorite;
}
