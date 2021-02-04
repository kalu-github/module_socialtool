package lib.kalu.socialtool.core;

public interface ISocialCallback {

    void onStart();

    void onSucc(String loginTokenCode);

    void onFail(String message);

    void onCancel();
}
