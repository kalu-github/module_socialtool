package lib.kalu.socialtool.params;

import androidx.annotation.Keep;

import java.io.Serializable;

/**
 * description: 发起参数 - 微信 - 分享到收藏
 * created by kalu on 2021-02-09
 */
@Keep
public class WechatParamsShareImageToFavorite extends WechatParamsShareImageToDefault implements Serializable {

    @Override
    public int platfromType() {
        return PLATFROM_WECHAT_WXSCENEFAVORITE;
    }
}
