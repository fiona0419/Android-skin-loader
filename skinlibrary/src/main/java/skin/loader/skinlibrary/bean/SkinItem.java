package skin.loader.skinlibrary.bean;

import android.view.View;

import java.util.List;

/**
 * Created by fuqiang.zhong on 2017/6/21.
 */

public class SkinItem {
    private List<SkinAttr> skinAttrs;
    private View view;

    public SkinItem(View view, List<SkinAttr> skinAttrs) {
        this.view = view;
        this.skinAttrs = skinAttrs;
    }

    public void applySkin() {
        for (SkinAttr skinAttr : skinAttrs) {
            skinAttr.applySkin(view);
        }
    }
}
