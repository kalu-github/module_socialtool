package lib.kalu.socialtool.listener;

/**
 * description: 分享状态变化监听
 * created by kalu on 2021-02-08
 */
public interface OnSocialChangeListener {

    void onStart();

    void onSucc(String loginTokenCode);

    void onFail(String message);

    void onCancel();
}
