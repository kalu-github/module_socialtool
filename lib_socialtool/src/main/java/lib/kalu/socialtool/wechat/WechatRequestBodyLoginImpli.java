package lib.kalu.socialtool.wechat;

import lib.kalu.socialtool.core.ISocialRequestBody;

/**
 * 描述 ：
 */
public class WechatRequestBodyLoginImpli implements ISocialRequestBody {

    private String reqScope;
    private String reqState;

    public String getReqScope() {
        return reqScope;
    }

    public void setReqScope(String reqScope) {
        this.reqScope = reqScope;
    }

    public String getReqState() {
        return reqState;
    }

    public void setReqState(String reqState) {
        this.reqState = reqState;
    }
}
