package lib.kalu.socialtool.params;

import java.io.Serializable;

import lib.kalu.socialtool.impl.WechatParamsImpl;

/**
 * 描述 ：
 */
public class WechatParamsLogin implements WechatParamsImpl, Serializable {

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

    @Override
    public int contentType() {
        return 0;
    }

    @Override
    public int sceneType() {
        return 0;
    }
}
