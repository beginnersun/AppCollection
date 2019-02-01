package com.example.myboy.appcollection.cardgame.base;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {

    Unbinder unbinder;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResourcesId(),container,false);
        unbinder = ButterKnife.bind(this,view);
        onCreateView(savedInstanceState);
        return view;
    }

    protected abstract void onCreateView(@Nullable Bundle savedInstanceState);

    protected abstract void setPresenter(BasePresenter presenter);

    protected abstract int getLayoutResourcesId();

    protected abstract String getNetWorkResourceUri();


    public void requestNetWorkData(){
        handleData("");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    protected abstract void handleData(String json);

    public void refresh(){

    }
}
