package lib.kalu.socialtool.params;

import androidx.annotation.Keep;

import java.io.Serializable;

/**
 * description: 发起参数 - 微信 - 网页 - 分享到朋友圈
 * created by kalu on 2021-02-09
 */
@Keep
public class WechatParamsShareWebpageToTimeline extends WechatParamsShareWebpageToDefault implements Serializable {

    @Override
    public int platfromType() {
        return PLATFROM_WECHAT_WXSCENETIMELINE;
    }
}
