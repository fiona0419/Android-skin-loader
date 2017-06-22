package skin.loader.skinlibrary;

import android.content.Context;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import skin.loader.skinlibrary.bean.SkinAttr;
import skin.loader.skinlibrary.bean.SkinItem;

/**
 * Created by fuqiang.zhong on 2017/6/21.
 */

public class SkinInflaterFactory implements LayoutInflaterFactory {
    private AppCompatActivity mAppCompatActivity;
    private HashMap<View, SkinItem> skinMap = new HashMap<>();

    public SkinInflaterFactory(AppCompatActivity appCompatActivity) {
        this.mAppCompatActivity = appCompatActivity;
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        boolean isSkinEnable = attrs.getAttributeBooleanValue(SkinConfig.NAMESPACE, SkinConfig.SKIN_ENABLE, false);

        if (isSkinEnable) {
            AppCompatDelegate delegate = mAppCompatActivity.getDelegate();
            View view = delegate.createView(parent, name, context, attrs);
            if (view != null) {
                parseSkinAttr(context, attrs, view);
                return view;
            }
        }
        return null;
    }

    private void parseSkinAttr(Context context, AttributeSet attrs, View view) {
        List<SkinAttr> skinAttrs = new ArrayList<>();
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            String attrName = attrs.getAttributeName(i);
            String attrValue = attrs.getAttributeValue(i);
            if (attrValue.startsWith("@")) {
                int resId = Integer.parseInt(attrValue.substring(1));
                String resName = context.getResources().getResourceEntryName(resId);
                String resType = context.getResources().getResourceTypeName(resId);
                SkinAttr skinAttr = new SkinAttr(resId, attrName, resName, resType);
                skinAttrs.add(skinAttr);
            }
        }
        SkinItem skinItem = new SkinItem(view, skinAttrs);
        skinMap.put(view, skinItem);

        if (!SkinConfig.isDefaultTheme(context)) {
            skinItem.applySkin();
        }
    }

    public void updateSkin() {
        Set<View> views = skinMap.keySet();
        for (View view : views) {
            skinMap.get(view).applySkin();
        }
    }
}
