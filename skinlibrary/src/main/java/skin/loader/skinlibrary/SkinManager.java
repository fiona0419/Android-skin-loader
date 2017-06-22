package skin.loader.skinlibrary;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.TextUtils;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuqiang.zhong on 2017/6/21.
 */

public class SkinManager {
    private static SkinManager mInstance;
    private Resources mSkinResource;
    private String mSkinPackageName;
    private Context mContext;

    private List<ISkinUpdate> mSkinObservers;

    private SkinManager() {
    }

    public static SkinManager getInstance() {
        if (mInstance == null) {
            mInstance = new SkinManager();
        }
        return mInstance;
    }

    public void init(Context context) {
        mContext = context;
        mSkinObservers = new ArrayList<>();
        applySkin(SkinConfig.getSkinFilePath(context));
    }

    public void applySkin(File skinFile) {
        if (skinFile != null) {
            applySkin(skinFile.getAbsolutePath());
        }
    }

    public void applySkin(String skinPath) {
        if (!TextUtils.isEmpty(skinPath)) {
            loadSkinResource(skinPath);
        }
    }

    private void loadSkinResource(final String skinPath) {
        new AsyncTask<String, Void, Resources>() {
            @Override
            protected void onPreExecute() {
                SkinConfig.saveSkinFilePath(mContext, skinPath);
            }

            @Override
            protected Resources doInBackground(String... params) {
                try {
                    PackageManager packageManager = mContext.getPackageManager();
                    PackageInfo packageInfo = packageManager.getPackageArchiveInfo(params[0], PackageManager.GET_ACTIVITIES);
                    mSkinPackageName = packageInfo.packageName;

                    AssetManager assetManager = AssetManager.class.newInstance();
                    Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
                    addAssetPath.invoke(assetManager, params[0]);

                    Resources res = mContext.getResources();
                    return new Resources(assetManager, res.getDisplayMetrics(), res.getConfiguration());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Resources resources) {
                if (resources != null) {
                    mSkinResource = resources;
                    notifySkinUpdate();
                }
            }
        }.execute(skinPath);
    }

    private void notifySkinUpdate() {
        for (ISkinUpdate observer : mSkinObservers) {
            observer.updateSkin();
        }
    }

    public void register(ISkinUpdate observer) {
        mSkinObservers.add(observer);
    }

    public void unregister(ISkinUpdate observer) {
        mSkinObservers.remove(observer);
    }

    public void restoreDefaultTheme() {
        mSkinResource = mContext.getResources();
        mSkinPackageName = mContext.getPackageName();
        notifySkinUpdate();
        SkinConfig.clearSkinFilePath(mContext);
    }

    public Drawable getDrawable(int resId, String resName, String resType) {
        Drawable drawable = null;
        if (mSkinResource != null) {
            try {
                int skinResId = mSkinResource.getIdentifier(resName, resType, mSkinPackageName);
                drawable = mSkinResource.getDrawable(skinResId);
            } catch (Resources.NotFoundException e) {
            }
        }
        if (drawable == null) {
            drawable = mContext.getResources().getDrawable(resId);
        }
        return drawable;
    }

    public int getColor(int resId, String resName, String resType) {
        int color = -1;
        if (mSkinResource != null) {
            try {
                int skinResId = mSkinResource.getIdentifier(resName, resType, mSkinPackageName);
                color = mSkinResource.getColor(skinResId);
            } catch (Resources.NotFoundException e) {
            }
        }
        if (color == -1) {
            color = mContext.getResources().getColor(resId);
        }
        return color;
    }
}
