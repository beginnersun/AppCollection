package com.example.myboy.appcollection.cardgame.activity.start.fragment.presenter;

import com.example.myboy.appcollection.cardgame.base.BasePresenter;
import com.example.myboy.appcollection.cardgame.bean.ContactBean;

import java.util.List;

public interface Presenter extends BasePresenter {

    void queryContacts();

    interface View{

        boolean isHavePermission();

        void setContactsData(String data);

        void haveNoneContact();

        void notHavePermission();

    }

}
