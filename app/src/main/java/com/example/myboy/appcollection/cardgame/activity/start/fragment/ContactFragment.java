package com.example.myboy.appcollection.cardgame.activity.start.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myboy.appcollection.R;
import com.example.myboy.appcollection.cardgame.activity.start.fragment.adapter.ContactAdapter;
import com.example.myboy.appcollection.cardgame.activity.start.fragment.presenter.ContactPresenter;
import com.example.myboy.appcollection.cardgame.activity.start.fragment.presenter.Presenter;
import com.example.myboy.appcollection.cardgame.base.BaseFragment;
import com.example.myboy.appcollection.cardgame.base.BasePresenter;
import com.example.myboy.appcollection.cardgame.bean.ContactBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class ContactFragment extends BaseFragment implements Presenter.View,ContactAdapter.OnItemInviteClickListener{

    @BindView(R.id.tv_none)
    TextView tvNone;
    @BindView(R.id.tv_open_permission)
    TextView tvOpenPermission;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.smartrefreshlayout)
    SmartRefreshLayout smartrefreshlayout;

    private LinearLayoutManager linearLayoutManager;
    private boolean isContactPermission = false;
    private ContactPresenter presenter;
    private ContactAdapter adapter;
    private ArrayList<ContactBean> beans;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setPresenter(new ContactPresenter(this));
        beans = new ArrayList<ContactBean>();
        adapter = new ContactAdapter(beans,getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.setAdapter(adapter);
        adapter.setClickListener(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void setPresenter(BasePresenter presenter) {
        this.presenter = (ContactPresenter) presenter;
    }

    public void setContactPermission(boolean contactPermission) {
        isContactPermission = contactPermission;
    }

    @Override
    public int getLayoutResourcesId() {
        return R.layout.fragment_contact;
    }

    @Override
    protected String getNetWorkResourceUri() {
        return "";
    }

    @Override
    protected void handleData(String json){

    }

    @Override
    public boolean isHavePermission() {
        return isContactPermission;
    }

    @Override
    public void setContactsData(List<ContactBean> data) {
        adapter.setDatas(data);
    }

    @Override
    public void haveNoneContact() {
        tvNone.setVisibility(View.VISIBLE);
        tvOpenPermission.setVisibility(View.GONE);
    }

    @Override
    public void notHavePermission() {
        tvNone.setVisibility(View.GONE);
        tvOpenPermission.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(int position) {
        ContactBean bean = beans.get(position);

    }
}
