package skin.loader.skinlibrary;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * Created by fuqiang.zhong on 2017/6/21.
 */

public class SkinConfig {
    public static final String NAMESPACE = "http://schemas.android.com/android/skin";
    public static final String SKIN_ENABLE = "enable";

    public static final String SKIN_FILE_PATH = "skin_file_path";

    public static void saveSkinFilePath(Context context, String skinPath) {
        SharedPreferences spf = context.getSharedPreferences(SKIN_FILE_PATH, Context.MODE_PRIVATE);
        spf.edit().putString(SKIN_FILE_PATH, skinPath).apply();
    }

    public static String getSkinFilePath(Context context) {
        SharedPreferences spf = context.getSharedPreferences(SKIN_FILE_PATH, Context.MODE_PRIVATE);
        return spf.getString(SKIN_FILE_PATH, "");
    }

    public static boolean isDefaultTheme(Context context) {
        String skinPath = getSkinFilePath(context);
        return TextUtils.isEmpty(skinPath);
    }

    public static void clearSkinFilePath(Context context) {
        saveSkinFilePath(context, "");
    }
}
