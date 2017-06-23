# Android-skin-loader

一、添加依赖
===

*  在project gradle中添加 

~~~java
    allprojects {
          repositories {
            ...
            maven { url 'https://jitpack.io' }
          }
        }
~~~
	
	
* 在module gradle中添加：  
~~~java
        dependencies {
            ...
	    compile 'com.github.fiona0419:android-skin-loader:847448b2da'
        }
~~~

二、使用
===

* 在Application中：  
~~~java
    @Override
    public void onCreate() {
        super.onCreate();
        ...
        SkinFileUtils.setUpSkinFile(this);
    }
~~~
    
* 需要替换皮肤的Activity继承SkinBaseActivity

* 在xml文件里：
~~~java
        添加xmlns:skin="http://schemas.android.com/android/skin"
        给需要替换皮肤的View添加属性skin:enable="true"
~~~

* 加载皮肤插件：  
~~~java
        SkinManager.getInstance().applySkin(skinPath); //skinPath是皮肤插件的文件路径
~~~
        
* 还原默认皮肤：  
~~~java
        SkinManager.getInstance().restoreDefaultTheme();
~~~
	
三、皮肤插件生成
===
~~~java
        新建一个application模块，只放需要替换的color和drawable资源，保持与默认资源一致的资源名称，编译生成的apk文件
	就是皮肤插件，可以将.apk后缀改为诸如.skin的形式。
~~~
