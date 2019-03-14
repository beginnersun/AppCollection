package com.example.myboy.appcollection;

import android.Manifest;
import android.app.Activity;
import android.app.usage.ExternalStorageStats;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myboy.appcollection.cardgame.activity.login.LoginActivity;
import com.example.myboy.appcollection.desktop.DeskTopActivity;
import com.example.myboy.appcollection.search.SortActivity;
import com.example.myboy.appcollection.videoplayer.VideoPlayerActivity;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.bugly.beta.Beta;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    int REQUEST_IMAGE = 2;  //请求系统图片
    int REQUEST_MUSIC = 3; //请求mp3文件
    String filePathMusic;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//
//        File file = new File("");
//        setBgMusic.setOnClickListener(new View.OnClickListener() { //点击选择音频文件
//            @Override
//            public void onClick(View view) {
//                requestPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA);
//            }
//        });

//        Intent intent = new Intent(this,VideoPlayerActivity.class);
//        intent.putExtra(VideoPlayerActivity.VIDEO_NAME,"名字");
//        intent.putExtra(VideoPlayerActivity.VIDEO_URL,"https://d2.xia12345.com/down/91/2019/01/Dx6e3Ggk.mp4");
//        startActivity(intent);

    }
    protected void requestPermissions(String... permissions){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){ //如果大于等于23才回动态申请否则
            if(permissions.length==1){ //如果权限只有一个 那就用一个的方法
                requestPermission(permissions[0]);
            }else {
                RxPermissions rxPermissions = new RxPermissions(this);
                rxPermissions.requestEachCombined(permissions).subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if(permission.granted){
                            String dir = Environment.getExternalStorageDirectory().getAbsolutePath();
                            dir = dir + File.separator + "gaga.mp3";
                            File file = new File(dir);
                            if(!file.exists()){
                                file.createNewFile();
                            }
                            Uri uri = Uri.fromFile(file);

//                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                            intent.setType("audio/*");
//                            startActivityForResult(intent,REQUEST_MUSIC);
                        }else{

                        }
                    }
                });
            }
        }else{ //  小于23 权限直接拥有
            for(String permission:permissions){

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("audio/*");
                startActivityForResult(intent,REQUEST_MUSIC);
            }
        }
    }
    /** 保存相机的图片 **/
    private void saveCameraImage(Intent data) {
        // 检查sd card是否存在
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Log.i(TAG, "sd card is not avaiable/writeable right now.");
            return;
        }
        // 为图片命名啊
        String name = "aaaa"
                + ".jpg";
        Bitmap bmp = (Bitmap) data.getExtras().get("data");// 解析返回的图片成bitmap

        // 保存文件
        FileOutputStream fos = null;
        File file = new File("/mnt/sdcard/test/");
        file.mkdirs();// 创建文件夹
        String fileName = "/mnt/sdcard/test/" + name;// 保存路径

        try {// 写入SD card
            fos = new FileOutputStream(fileName);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }// 显示图片
//        ((ImageView) findViewById(R.id.show_image)).setImageBitmap(bmp);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_MUSIC && RESULT_OK == resultCode && null!=data){ //获取选择的音频文件
            Uri uri = data.getData();
            String filePath[] = {MediaStore.Audio.Media.DATA};
            Cursor cursor = getContentResolver().query(uri,filePath, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePath[0]);
            filePathMusic = cursor.getString(columnIndex);
            Log.d("SetMediaPlayerActivity:","选择的音频文件为"+filePathMusic);
            Toast.makeText(this,"选择的音频文件为"+filePathMusic,Toast.LENGTH_LONG).show();
            Uri music = Uri.fromFile(new File(filePathMusic));
            cursor.close();
        }
    }

    private void requestPermission(String permission){
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(permission).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if(aBoolean){
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("audio/*");
                    startActivityForResult(intent,REQUEST_MUSIC);
                }else {
                    Toast.makeText(MainActivity.this,"请求权限失败",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    /**
     *
     * @param menu
     * @return false 不显示  true显示
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent();
        switch (item.getItemId()){
            case R.id.item_cardDemo:
                intent.setClass(this,LoginActivity.class);
                break;
            case R.id.item_jniDemo:
                break;
            case R.id.item_MapDemo:
                break;
            case R.id.item_mediaPlayerDemo:
                break;
            case R.id.item_videoPlayerDemo:
                break;
            case R.id.item_pictureDemo:
                break;
            case R.id.item_netWorkDemo:
                break;
            case R.id.item_socketDemo:
                break;
            case R.id.item_sortAlgorithm:
                intent.setClass(this,SortActivity.class);
                break;
            case R.id.item_searchAlgorithm:
                break;
            case R.id.item_desktop:
                intent.setClass(this,DeskTopActivity.class);
                break;
        }
        startActivity(intent);
        finish();
        return super.onOptionsItemSelected(item);
    }
}
