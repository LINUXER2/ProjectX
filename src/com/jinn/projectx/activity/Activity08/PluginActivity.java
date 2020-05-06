package com.jinn.projectx.activity.Activity08;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;

import com.jinn.projectx.R;
import com.jinn.projectx.activity.Utils.HeavyWorkThread;
import com.jinn.projectx.activity.Utils.Logit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;
import dalvik.system.DexFile;

/**
 *  动态加载技术
 */
public class PluginActivity extends Activity {

    private Context mContext;
    private final String TAG="jinn";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin);
        mContext  = this;
        HeavyWorkThread.getHandler().post(new Runnable() {
            @Override
            public void run() {
                getRemoteResource("com.vivo.hiboard");
                copyFileFromAssets("pluginmodule-debug.apk");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String path = Environment.getExternalStorageDirectory()+"/"+"pluginmodule-debug.apk";
                       // patchClassLoader(mContext.getClassLoader(),new File(Environment.getExternalStorageDirectory()+"/"+"pluginmodule-debug.apk"),mContext);
                        DexClassLoader dexClassLoader = new DexClassLoader(path,null,null,mContext.getClassLoader());
                        try{
                             Class<?>mLoadedClass = dexClassLoader.loadClass("com.jinn.pluginmodule.ViewManager");
                             Object loadInstance = mLoadedClass.newInstance();
                             Method method = mLoadedClass.getMethod("getView",Context.class);
                             final Object object1 = method.invoke(loadInstance,mContext);
                             if(object1 instanceof View){
                                PluginActivity.this.addContentView((View)object1,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                             }
                         }catch (Exception e){
                             Logit.i(TAG,"load class error:"+e.toString());
                         }

                    }
                });
            }
        });
    }

    private void getRemoteResource(String pkgName){
        try{
          Context context = mContext.createPackageContext(pkgName,CONTEXT_INCLUDE_CODE|CONTEXT_IGNORE_SECURITY);
          int id = context.getResources().getIdentifier("app_name","string",pkgName);
          String remoteName = context.getString(id);
          Logit.d(TAG,"getRemoteResource:"+remoteName);
        }catch (PackageManager.NameNotFoundException e){
          Logit.i(TAG,"name not found:"+e.toString());
        }

    }

    /**
     * 将assert下的文件保存在sd卡
     * @param fileName
     */
    private void copyFileFromAssets(String fileName){
        if(ContextCompat.checkSelfPermission(PluginActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=
        PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(PluginActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
        AssetManager assetManager = mContext.getAssets();
        InputStream ins = null;
        FileOutputStream fos=null;
        String path = Environment.getExternalStorageDirectory()+"/"+fileName;
        File file = new File(path);
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        try{
            if(!file.exists()){
                file.createNewFile();
            }else{
                Logit.d(TAG,"file already exists");
                return;
            }
             fos= new FileOutputStream(path);
            ins = assetManager.open(fileName);
            int len = 0;
            byte []buffer = new byte[1024*512];
            while ((len=ins.read(buffer))!=-1){
                fos.write(buffer,0,len);
            }
            fos.flush();
        }catch(Exception e){
            Logit.i(TAG,"open file error:"+e.toString());
        }finally {
            if(ins!=null){
                try{
                    ins.close();
                }catch (Exception e){
                    Logit.i(TAG,"close erroe:"+e.toString());
                }
            }

            if(fos!=null){
                try{
                    fos.close();
                }catch (Exception e){
                    Logit.i(TAG,"close error:"+e.toString());
                }
            }
        }

    }


    public void patchClassLoader(ClassLoader cl, File optDexFile, Context context) {
        Logit.d(TAG, "patchClassloader, need load apk file exists： " + optDexFile.exists() + ", " +
                "path: " + optDexFile.getAbsolutePath());
        try {
            // 获取 BaseDexClassLoader : pathList
            Field pathListField = DexClassLoader.class.getSuperclass().getDeclaredField("pathList");
            pathListField.setAccessible(true);
            Object pathListObj = pathListField.get(cl);

            // 获取 PathList: Element[] dexElements
            Field dexElementArray = pathListObj.getClass().getDeclaredField("dexElements");
            dexElementArray.setAccessible(true);
            Object[] dexElements = (Object[]) dexElementArray.get(pathListObj);

            // Element 类型
            Class<?> elementClass = dexElements.getClass().getComponentType();

            // 创建一个数组, 用来替换原始的数组
            Object[] newElements = (Object[]) Array.newInstance(elementClass, dexElements.length
                    + 1);

            // 构造插件Element(File file, boolean isDirectory, File zip, DexFile dexFile) 这个构造函数
            Constructor<?> constructor = null;
            Object o = null;
            try {
                constructor = elementClass.getConstructor(DexFile.class, File.class);
                o = constructor.newInstance(DexFile.loadDex(optDexFile.getAbsolutePath(),
                        context.getDir("outputDir", 0).getAbsolutePath(), 0), null);
            } catch (NoSuchMethodException e) {
                constructor = elementClass.getConstructor(File.class, boolean.class, File.class,
                        DexFile.class);
                o = constructor.newInstance(new File("/system/lib64"), true, null, DexFile
                        .loadDex(optDexFile.getAbsolutePath(),
                                context.getDir("outputDir", 0).getAbsolutePath(), 0));
            }

            Object[] toAddElementArray = new Object[]{o};
            // 把原始的elements复制进去
            System.arraycopy(dexElements, 0, newElements, 0, dexElements.length);
            // 插件的那个element复制进去
            System.arraycopy(toAddElementArray, 0, newElements, dexElements.length,
                    toAddElementArray.length);

            // 替换
            dexElementArray.set(pathListObj, newElements);
            Logit.i(TAG, "dexPathList: " + pathListObj);
        } catch (Exception e) {
            Logit.d(TAG, "patch loaded apk fail:"+e);
        }

    }
}
