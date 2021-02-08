package lib.kalu.socialtool.impl;

import java.io.Serializable;

/**
 * description: 发起请求参数
 * created by kalu on 2021-02-08
 */
public interface WechatParamsImpl extends Serializable {

    int contentType();

    int sceneType();
}
