package lib.kalu.socialtool.params;

import java.io.Serializable;

/**
 * description: 图片类型分享示例
 * created by kalu on 2021-02-08
 */
public class WechatParamsShareImage implements WechatParamsShare, Serializable {

    private int sceneType = SCENE_ONLY_WXSCENESESSION;
    private byte[] imageData = null;
    private String imagePath = null;

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getSceneType() {
        return sceneType;
    }

    public void setSceneType(int sceneType) {
        this.sceneType = sceneType;
    }

    @Override
    public int contentType() {
        return CONTENT_ONLY_IMAGE;
    }

    @Override
    public int sceneType() {
        return getSceneType();
    }
}
