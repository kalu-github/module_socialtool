package lib.kalu.socialtool.create;

import androidx.annotation.NonNull;

import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;

import lib.kalu.socialtool.impl.WechatParamsImpl;

public interface WechatMedia {

    WXMediaMessage create();
}
