package skin.loader.skinlibrary.bean;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import skin.loader.skinlibrary.SkinManager;


/**
 * Created by fuqiang.zhong on 2017/6/21.
 */

public class SkinAttr {
    public static final String ATTR_SRC = "src";
    public static final String ATTR_BACKGROUND = "background";
    public static final String ATTR_TEXTCOLOR = "textcolor";

    private int resId;
    private String attrName;
    private String resName;
    private String resType;

    public SkinAttr(int resId, String attrName, String resName, String resType) {
        this.resId = resId;
        this.attrName = attrName;
        this.resName = resName;
        this.resType = resType;
    }

    public void applySkin(View view) {
        SkinManager skinManager = SkinManager.getInstance();
        switch (attrName) {
            case ATTR_SRC:
                if (view instanceof ImageView) {
                    ((ImageView) view).setImageDrawable(skinManager.getDrawable(resId, resName, resType));
                }
                break;
            case ATTR_BACKGROUND:
                view.setBackgroundDrawable(skinManager.getDrawable(resId, resName, resType));
                break;
            case ATTR_TEXTCOLOR:
                if (view instanceof TextView) {
                    ((TextView) view).setTextColor(skinManager.getColor(resId, resName, resType));
                }
                break;
        }
    }
}
