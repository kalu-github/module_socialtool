package lib.kalu.socialtool.http;

import androidx.annotation.Keep;

import java.io.Serializable;

/**
 * description: 返回数据
 * create by kalu on 2018/12/28 13:15
 */

@Keep
public interface HttpSocialToolModelImpl<T> extends Serializable {

    /**
     * 成功
     *
     * @return
     */
    boolean isSucc();

    /**
     * 身份过期失效
     *
     * @return
     */
    boolean isInvalid();

    /**
     * 提示信息
     *
     * @return
     */
    String getMessage();

    /**
     * 网络错误
     */
    boolean isHttpError();

    /**
     * 返回数据
     */
    T getResponse();
}
