#
#### Demo

![image](https://github.com/153437803/SocialTool/blob/master/image20210208172000.gif )

#
#### 微信文档
```
https://bintray.com/wechat-sdk-team/maven
https://developers.weixin.qq.com/doc/oplatform/Mobile_App/Share_and_Favorites/Android.html
```

#
#### 微信：分享图片
```
        WechatClient client = WechatClient.getInstance(this, "wxAppId");
        WechatParamsShareImageToDefault params = new WechatParamsShareImageToDefault();

        SocialManage.share(this, client, params, new OnSocialChangeListener() {
            @Override
            public void onStart() {
                Log.e("kalu", "wxShare1 => onStart");
            }

            @Override
            public void onSucc(String loginTokenCode) {
                Log.e("kalu", "wxShare1 => onSucc");
            }

            @Override
            public void onFail(String message) {
                Log.e("kalu", "wxShare1 => onFail");
            }

            @Override
            public void onCancel() {
                Log.e("kalu", "wxShare1 => onCancel");
            }
        });
```


#
#### 微信：分享网页
```
        WechatClient client = WechatClient.getInstance(this, "wxAppId");
        WechatParamsShareWebpageToDefault params = new WechatParamsShareWebpageToDefault();

        SocialManage.share(this, client, params, new OnSocialChangeListener() {
            @Override
            public void onStart() {
                Log.e("kalu", "wxShare1 => onStart");
            }

            @Override
            public void onSucc(String loginTokenCode) {
                Log.e("kalu", "wxShare1 => onSucc");
            }

            @Override
            public void onFail(String message) {
                Log.e("kalu", "wxShare1 => onFail");
            }

            @Override
            public void onCancel() {
                Log.e("kalu", "wxShare1 => onCancel");
            }
        });
```

#
#### 支付
```
微信, 支付宝, 银联
```
