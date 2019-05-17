package com.example.myboy.appcollection;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.usage.ExternalStorageStats;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.myboy.appcollection.cardgame.activity.login.LoginActivity;
import com.example.myboy.appcollection.cardgame.weigets.VerticalBannerView;
import com.example.myboy.appcollection.databinding.WeatherActivity;
import com.example.myboy.appcollection.desktop.DeskTopActivity;
import com.example.myboy.appcollection.search.SortActivity;
import com.example.myboy.appcollection.videoplayer.VideoPlayerActivity;
import com.example.myboy.appcollection.vpn.VpnUtil;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.bugly.beta.Beta;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    int REQUEST_IMAGE = 2;  //请求系统图片
    int REQUEST_MUSIC = 3; //请求mp3文件
    String filePathMusic;
    private static final String TAG = "MainActivity";
    VerticalBannerView verticalBannerView;
    TextView textView;
    LineChart lineChart;

    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = findViewById(R.id.img);

//        img.setImageResource(R.mipmap.a10a051764b028bc7dc8fa24e9c28313);

//        Glide.with(this).load(R.mipmap.a10a051764b028bc7dc8fa24e9c28313).into(img);
//        Intent intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
//        startActivity(intent);

//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage("当前账户未通过认证，不可添加账户信息，是否去重新提交认证？")
//                .setCancelable(false)
//                .setPositiveButton("去认证", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                    }
//                }).show();
//        Dialog dialog = builder.create();
//        dialog.show();

//startActivity(new Intent(this, com.example.myboy.appcollection.demo.activity.login.LoginActivity.class));
//
//        lineChart = findViewById(R.id.lineChart);
//
//        lineChart.setTouchEnabled(false);
//
//        // enable scaling and dragging
//        lineChart.setDragEnabled(false);
//        lineChart.setScaleEnabled(false);
//        // chart.setScaleXEnabled(true);
//        // chart.setScaleYEnabled(true);
//
//        // force pinch zoom along both axis
//        lineChart.setPinchZoom(false);
//        lineChart.setBackgroundColor(Color.WHITE);
//        lineChart.setDrawBorders(false);
//
//
//        ArrayList<String> mList = new ArrayList<>();
//        XAxis xAxis = lineChart.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); //设置x轴在下方  默认为上方
//        xAxis.setGranularity(1f);
//        /**
//         * 设置X轴值为字符串
//         */
//        mList.add("");
//        mList.add("8-1");
//        mList.add("8-2");
//        mList.add("8-3");
//        mList.add("8-4");
//        mList.add("8-5");
//        mList.add("8-6");
//        xAxis.setValueFormatter(new IAxisValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                return mList.get((int) value); //mList为存有月份的集合
//            }
//        });

        /**
         * 设置Y轴为字符串
         */
//        List<String> left = new ArrayList<>();
//        left.add("0K");
//        left.add("200");
//        left.add("300");
//        left.add("400");
//        YAxis leftYAxis = lineChart.getAxisLeft();
//        lineChart.getAxisRight().setEnabled(false);
//
//        xAxis.setDrawGridLines(false);
//        leftYAxis.setDrawGridLines(false);
//        leftYAxis.setValueFormatter(new IAxisValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                return value+"";
//            }
//        });
        /**
         * 设置网格属性
         */
//        leftYAxis.setGranularity(1f);
//        leftYAxis.setLabelCount(4,true);
//        leftYAxis.setAxisMinimum(0);
//        leftYAxis.setAxisMaximum(400);
//        leftYAxis.setAxisLineColor(Color.GREEN); //Y轴颜色
//
//        /**
//         * 隐藏曲线图的标签
//         */
//        Legend legend = lineChart.getLegend();
//        legend.setEnabled(false);
//
//        Description description = new Description();
//        description.setEnabled(false);
//        lineChart.setDescription(description);
//
//        // 折线图的线条设置
////一个LineDataSet就是一条线
//        List<Entry> entries = new ArrayList<>();
//        for (int i = 0; i < 7; i++) {
//            entries.add(new Entry(i, (float) (Math.random()) * 400));
//        }
//        LineDataSet lineDataSet = new LineDataSet(entries, "");
////设置曲线值的圆点是实心还是空心
//        lineDataSet.setDrawCircleHole(false);
////设置显示值的字体大小
//        lineDataSet.setValueTextSize(9f);
//        lineDataSet.setDrawValues(false);
////线模式为圆滑曲线（默认折线）
//        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
//        //不显示点
//        lineDataSet.setDrawCircles(false);
//        LineData lineData = new LineData(lineDataSet);
//        lineChart.setData(lineData);

//        GlideApp.with(this).asBitmap().load(R.drawable.circle).transition(new BitmapTransitionOptions().crossFade())

//
//D
//        verticalBannerView = findViewById(R.id.verticalview);
//
//        List<Bean> title = new ArrayList<>();
//        title.add(new Bean("第一个之一","第一个之二"));
//        title.add(new Bean("第二个之一","第二个之二"));
//        title.add(new Bean("第三个之一","第三个之二"));
//        verticalBannerView.setDatas(title);
//        verticalBannerView.setTime(1*1000,3*1000);
//        verticalBannerView.setViewFactory(new ViewFactory(new SoftReference<>(this)));

//        findViewById(R.id.hh).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                VpnUtil.init(MainActivity.this);
//                Object vpnProfile = VpnUtil.getVpnProfile();
//                if (vpnProfile == null) {
//                    vpnProfile = VpnUtil.createVpnProfile("vpn1", "host", "username", "password");
//                } else {
//                    VpnUtil.setParams(vpnProfile,"vpn2","host","username","password");
//                }
//                VpnUtil.connect(MainActivity.this,vpnProfile);
//            }
//        });
//        verticalBannerView.setFactory(new VerticalBannerView.VerticalViewFactory() {
//
//            @Override
//            protected View createView(Object o) {
//                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_alipay,null);
//                return view;
//            }
//        });
    }

    static class Bean {
        private String one;
        private String two;

        public Bean(String one, String two) {
            this.one = one;
            this.two = two;
        }

        public String getOne() {
            return one;
        }

        public void setOne(String one) {
            this.one = one;
        }

        public String getTwo() {
            return two;
        }

        public void setTwo(String two) {
            this.two = two;
        }
    }

    static class ViewFactory extends VerticalBannerView.VerticalViewFactory<Bean> {
        private SoftReference<Context> mContext;

        public ViewFactory(SoftReference<Context> mContext) {
            this.mContext = mContext;
        }

        @Override
        protected void refreshView(View view, Bean bean) {
            if (view != null) {
                TextView one = view.findViewById(R.id.one);
                TextView two = view.findViewById(R.id.two);
                one.setText(bean.getOne());
                two.setText(bean.getTwo());
            }
        }

        @Override
        public View makeView() {
            View view = null;
            if (mContext.get() != null) {
                view = LayoutInflater.from(mContext.get()).inflate(R.layout.item_vertical, null);
            }
            return view;
        }
    }

    protected void requestPermissions(String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { //如果大于等于23才回动态申请否则
            if (permissions.length == 1) { //如果权限只有一个 那就用一个的方法
                requestPermission(permissions[0]);
            } else {
                RxPermissions rxPermissions = new RxPermissions(this);
                rxPermissions.requestEachCombined(permissions).subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            String dir = Environment.getExternalStorageDirectory().getAbsolutePath();
                            dir = dir + File.separator + "gaga.mp3";
                            File file = new File(dir);
                            if (!file.exists()) {
                                file.createNewFile();
                            }
                            Uri uri = Uri.fromFile(file);

//                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                            intent.setType("audio/*");
//                            startActivityForResult(intent,REQUEST_MUSIC);
                        } else {

                        }
                    }
                });
            }
        } else { //  小于23 权限直接拥有
            for (String permission : permissions) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("audio/*");
                startActivityForResult(intent, REQUEST_MUSIC);
            }
        }
    }

    /**
     * 保存相机的图片
     **/
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
        if (requestCode == REQUEST_MUSIC && RESULT_OK == resultCode && null != data) { //获取选择的音频文件
            Uri uri = data.getData();
            String filePath[] = {MediaStore.Audio.Media.DATA};
            Cursor cursor = getContentResolver().query(uri, filePath, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePath[0]);
            filePathMusic = cursor.getString(columnIndex);
            Log.d("SetMediaPlayerActivity:", "选择的音频文件为" + filePathMusic);
            Toast.makeText(this, "选择的音频文件为" + filePathMusic, Toast.LENGTH_LONG).show();
            Uri music = Uri.fromFile(new File(filePathMusic));
            cursor.close();
        }
    }

    private void requestPermission(String permission) {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(permission).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("audio/*");
                    startActivityForResult(intent, REQUEST_MUSIC);
                } else {
                    Toast.makeText(MainActivity.this, "请求权限失败", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    /**
     * @param menu
     * @return false 不显示  true显示
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent();
        switch (item.getItemId()) {
            case R.id.item_cardDemo:
                intent.setClass(this, LoginActivity.class);
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
                intent.setClass(this, SortActivity.class);
                break;
            case R.id.item_searchAlgorithm:
                break;
            case R.id.item_desktop:
                intent.setClass(this, DeskTopActivity.class);
                break;
        }
        startActivity(intent);
        finish();
        return super.onOptionsItemSelected(item);
    }
}
