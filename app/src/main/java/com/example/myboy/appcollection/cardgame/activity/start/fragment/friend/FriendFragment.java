package com.example.myboy.appcollection.cardgame.activity.start.fragment.friend;

import android.os.Bundle;
import com.example.myboy.appcollection.R;
import com.example.myboy.appcollection.cardgame.base.BaseFragment;
import com.example.myboy.appcollection.cardgame.base.BasePresenter;
import androidx.annotation.Nullable;

public class FriendFragment extends BaseFragment {

    @Override
    protected void onCreateView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void setPresenter(BasePresenter presenter) {

    }

    @Override
    protected int getLayoutResourcesId() {
        return R.layout.fragment_friends;
    }

    @Override
    protected String getNetWorkResourceUri() {
        return null;
    }

    @Override
    protected void handleData(String json) {

    }
}
