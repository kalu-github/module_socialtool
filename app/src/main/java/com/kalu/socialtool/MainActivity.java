package com.kalu.socialtool;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import lib.kalu.socialtool.SocialManage;
import lib.kalu.socialtool.client.WechatClient;
import lib.kalu.socialtool.listener.OnSocialChangeListener;
import lib.kalu.socialtool.params.WechatParamsShareImageToDefault;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 选择图片
        findViewById(R.id.action1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                wxShare1();
            }
        });

        // 选择图片
        findViewById(R.id.action2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                wxShare2();
            }
        });

        // 选择图片
        findViewById(R.id.action3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                wxShare3();
            }
        });
    }


    private void wxShare1() {

        WechatClient client = WechatClient.getInstance(this, "wx08e09091ee25095f");
        WechatParamsShareImageToDefault params = new WechatParamsShareImageToDefault();
        params.setImagePath("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fa4.att.hudong.com%2F27%2F67%2F01300000921826141299672233506.jpg&refer=http%3A%2F%2Fa4.att.hudong.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1615365854&t=a8c62191e074594745e87c140732d2e9");

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
    }

    private void wxShare2() {

        try {

            InputStream inputStream = getResources().openRawResource(R.raw.src);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buff = new byte[100];
            int rc = 0;
            while ((rc = inputStream.read(buff, 0, 100)) > 0) {
                baos.write(buff, 0, rc);
            }

            byte[] bytes = baos.toByteArray();

            WechatClient client = WechatClient.getInstance(this, "wx08e09091ee25095f");
            WechatParamsShareImageToDefault params = new WechatParamsShareImageToDefault();
            params.setImageData(bytes);

            SocialManage.share(this, client, params, new OnSocialChangeListener() {
                @Override
                public void onStart() {
                    Log.e("kalu", "wxShare2 => onStart");
                }

                @Override
                public void onSucc(String loginTokenCode) {
                    Log.e("kalu", "wxShare2 => onSucc");
                }

                @Override
                public void onFail(String message) {
                    Log.e("kalu", "wxShare2 => onFail");
                }

                @Override
                public void onCancel() {
                    Log.e("kalu", "wxShare2 => onCancel");
                }
            });

        }catch (Exception e){
            Log.e("kalu", "wxShare2 => message = "+e.getMessage(), e);
        }
    }

    private void wxShare3() {

        try {

            InputStream inputStream = getResources().openRawResource(R.raw.big);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buff = new byte[100];
            int rc = 0;
            while ((rc = inputStream.read(buff, 0, 100)) > 0) {
                baos.write(buff, 0, rc);
            }

            byte[] bytes = baos.toByteArray();

            WechatClient client = WechatClient.getInstance(this, "wx08e09091ee25095f");
            WechatParamsShareImageToDefault params = new WechatParamsShareImageToDefault();
            params.setImageData(bytes);

            SocialManage.share(this, client, params, new OnSocialChangeListener() {
                @Override
                public void onStart() {
                    Log.e("kalu", "wxShare3 => onStart");
                }

                @Override
                public void onSucc(String loginTokenCode) {
                    Log.e("kalu", "wxShare3 => onSucc");
                }

                @Override
                public void onFail(String message) {
                    Log.e("kalu", "wxShare3 => onFail");
                }

                @Override
                public void onCancel() {
                    Log.e("kalu", "wxShare3 => onCancel");
                }
            });

        }catch (Exception e){
            Log.e("kalu", "wxShare3 => message = "+e.getMessage(), e);
        }
    }
}