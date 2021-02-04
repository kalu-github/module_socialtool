package lib.kalu.socialtool.model;

import androidx.annotation.Keep;

import lib.kalu.socialtool.http.HttpSocialToolModelImpl;

@Keep
public class WechatSdkTokenModel implements HttpSocialToolModelImpl<WechatSdkLoginModel> {

    /**
     * access_token : 26_k7wErPQ-6NWH83XR0qVeyjeZ4-57EIoVS3qQnymiXOz7D9e7XDoYUSddE3nzHdu_ble1OUsHmy7pKonpqKcFSrzg-tosS6-CR2lWKjzi84M
     * expires_in : 7200
     * refresh_token : 26_4Lv8vbLLLKI8ok7l2Gaxcg0zjNWUh3qV98avcXfSL-MXQiAGuF0vn1AyH2j9lOLNrF6PCCc9DwFeFTcIsW7lxYewDqvzuZqkIiAGKfmQRXo
     * openid : oaw5ouFoJUbad7JYeqIgHuO1_Q_A
     * scope : snsapi_userinfo
     * unionid : o8urOvurS5ezNSuh-07DZ4TvlGv4
     */

    private String access_token;
    private int expires_in;
    private String refresh_token;
    private String openid;
    private String scope;
    private String unionid;

    @Override
    public boolean isSucc() {
        return true;
    }

    @Override
    public boolean isInvalid() {
        return false;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public boolean isHttpError() {
        return false;
    }

    @Override
    public WechatSdkLoginModel getResponse() {

        WechatSdkLoginModel wechatSdkLoginModel = new WechatSdkLoginModel();
        wechatSdkLoginModel.setAccess_token(access_token);
        wechatSdkLoginModel.setExpires_in(expires_in);
        wechatSdkLoginModel.setOpenid(openid);
        wechatSdkLoginModel.setRefresh_token(refresh_token);
        wechatSdkLoginModel.setScope(scope);
        wechatSdkLoginModel.setUnionid(unionid);
        return wechatSdkLoginModel;
    }

    /*********************************************************************************************/

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
