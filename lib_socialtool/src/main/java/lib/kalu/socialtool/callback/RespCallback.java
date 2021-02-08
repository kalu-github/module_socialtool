package lib.kalu.socialtool.callback;

/**
 * description: SDK 回调信息
 * created by kalu on 2021-02-08
 */
public interface RespCallback {

    int SDK_CALLBACK_TYPE_LOGIN = 201;
    int SDK_CALLBACK_TYPE_SHARE = 202;
    int SDK_CALLBACK_TYPE_PAYMENT = 203;

    void onResp(int callbackType, int errCode, String errStr, String resultCode);
}
