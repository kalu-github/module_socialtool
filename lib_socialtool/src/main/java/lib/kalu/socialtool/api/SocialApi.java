package lib.kalu.socialtool.api;

import lib.kalu.socialtool.model.WechatSdkTokenModel;

public interface SocialApi {

    /**
     * 微信获取openid
     * ---------------------------------------------------------
     * <p>
     * request:
     * <p>
     * loginUrl.append("https://api.weixin.qq.com/sns/oauth2/access_token")
     * .append("?appid=")
     * .append(Constant.APP_ID)
     * .append("&secret=")
     * .append(Constant.SECRET)
     * .append("&code=")
     * .append(code)
     * .append("&grant_type=authorization_code");
     * ---------------------------------------------------------
     * <p>
     * respons:
     * <p>
     * try {
     * JSONObject jsonObject = new JSONObject(response);
     * access = jsonObject.getString("access_token");
     * openId = jsonObject.getString("openid");
     * } catch (JSONException e) {
     * e.printStackTrace();
     * }
     * ---------------------------------------------------------
     */
//    @GET()
//    Observable<HttpModel<Object>> wechatAccessToken(@Url String url);
//    @POST()
//    Observable<WechatSdkTokenModel> wechatAccessToken(@Url String url, @Body RequestBody body);

    /**
     * 微信获取用户信息
     * ---------------------------------------------------------
     *
     * request:
     *
     * access_token=" + access + "&openid=" + openId
     * ---------------------------------------------------------
     *
     * respons:
     *
     * try {
     * JSONObject jsonObject = new JSONObject(responses);
     * <p>
     * openid = jsonObject.getString("openid");
     * nickName = jsonObject.getString("nickname");
     * sex = jsonObject.getString("sex");
     * city = jsonObject.getString("city");
     * province = jsonObject.getString("province");
     * country = jsonObject.getString("country");
     * headimgurl = jsonObject.getString("headimgurl");
     * unionid = jsonObject.getString("unionid");
     * loadNetData(1, openid, nickName, sex, province,
     * city, country, headimgurl, unionid);
     * <p>
     * } catch (JSONException e) {
     * e.printStackTrace();
     * }
     * ---------------------------------------------------------
     */

//    @POST()
//    Observable<HttpModel<Object>> wechatUserInfo(@Url String url, @Body RequestBody body);

}
