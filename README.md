# Android-skin-loader

一、添加依赖
===

**①在project gradle中添加：  **


        ~~~allprojects {
          repositories {
            ...
            maven { url 'https://jitpack.io' }
          }
        }~~~
	
	
**-+②在module gradle中添加：  **
        dependencies {
            ...
	    compile 'com.github.fiona0419:android-skin-loader:847448b2da'
        }
       

二、使用
===

**①在Application中：  *
    @Override
    public void onCreate() {
        super.onCreate();
        ...
        SkinFileUtils.setUpSkinFile(this);
    }
    
**②需要替换皮肤的Activity继承SkinBaseActivity*

**③在xml文件里，  *
        添加xmlns:skin="http://schemas.android.com/android/skin"
        给需要替换皮肤的View添加属性skin:enable="true"

**④加载皮肤插件：  *
        SkinManager.getInstance().applySkin(skinPath); //skinPath是皮肤插件的文件路径
        
**⑤还原默认皮肤：  *
        SkinManager.getInstance().restoreDefaultTheme();
        
	
三、皮肤插件生成
===

        新建一个application模块，只放需要替换的color和drawable资源，保持与默认资源一致的资源名称，编译生成的apk文件就是皮肤插件，
	可以将.apk后缀改为诸如.skin的形式。

