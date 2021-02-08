package lib.kalu.socialtool.exception;

/**
 * description: 异常信息封装
 * created by kalu on 2021-02-08
 */
public class SocialException extends Exception {

    private int mCode;

    public SocialException(String message, int code) {
        super(message);
        mCode = code;
    }

    public int getCode() {
        return mCode;
    }

    public void setCode(int code) {
        mCode = code;
    }
}
