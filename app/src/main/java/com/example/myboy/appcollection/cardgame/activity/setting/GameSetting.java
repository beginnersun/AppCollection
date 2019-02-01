package com.example.myboy.appcollection.cardgame.activity.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.myboy.appcollection.R;
import com.example.myboy.appcollection.cardgame.activity.setting.presenter.GamePresenter;
import com.example.myboy.appcollection.cardgame.activity.setting.presenter.Presenter;
import com.example.myboy.appcollection.cardgame.base.BaseActivity;
import com.example.myboy.appcollection.cardgame.base.BasePresenter;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

public class GameSetting extends BaseActivity implements Presenter.View {

    @BindView(R.id.setting_name)
    LinearLayout settingName;
    @BindView(R.id.seek_volume)
    SeekBar seekVolume;
    @BindView(R.id.check_sound)
    CheckBox checkSound;
    @BindView(R.id.check_bg_music)
    CheckBox checkBgMusic;
    @BindView(R.id.tv_storage)
    TextView tvStorage;

    GamePresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setPresenter(new GamePresenter(this));
        super.onCreate(savedInstanceState);

        seekVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                // TODO: 2019/1/24 正在拖动中，如果有背景音乐或者音效等功能那么改变音量
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                presenter.setVolume(seekBar.getProgress());
            }
        });
    }

    @Override
    protected void requestPermissionFailed(String permission) {

    }

    @Override
    protected void requestPermissionSuccess(String permission) {
    }

    @Override
    protected void setPresenter(BasePresenter basePresenter) {
        presenter = (GamePresenter) basePresenter;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void queryInfo() {
        presenter.querySetting();
    }

    @OnClick({R.id.check_sound, R.id.check_bg_music, R.id.tv_storage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.check_sound:
                presenter.setSound(checkSound.isChecked());
                break;
            case R.id.check_bg_music:
                presenter.setMusicBg(checkBgMusic.isChecked());
                break;
            case R.id.tv_storage:
                break;
        }
    }

    @Override
    public void setVolume(int progress) {
        seekVolume.setProgress(progress);
    }

    @Override
    public void setMusicBg(boolean open) {
        checkBgMusic.setChecked(open);
    }

    @Override
    public void setSound(boolean open) {
        checkSound.setChecked(open);
    }
}
