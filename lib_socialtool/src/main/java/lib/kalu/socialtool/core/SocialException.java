package lib.kalu.socialtool.core;

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
