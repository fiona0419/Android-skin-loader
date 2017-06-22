package skin.loader.skinlibrary.base;

import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;

import skin.loader.skinlibrary.ISkinUpdate;
import skin.loader.skinlibrary.SkinInflaterFactory;
import skin.loader.skinlibrary.SkinManager;


public class SkinBaseActivity extends AppCompatActivity implements ISkinUpdate {
    private SkinInflaterFactory mSkinInflaterFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSkinInflaterFactory = new SkinInflaterFactory(this);
        LayoutInflaterCompat.setFactory(getLayoutInflater(), mSkinInflaterFactory);
        super.onCreate(savedInstanceState);
        SkinManager.getInstance().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinManager.getInstance().unregister(this);
    }

    @Override
    public void updateSkin() {
        mSkinInflaterFactory.updateSkin();
    }
}
