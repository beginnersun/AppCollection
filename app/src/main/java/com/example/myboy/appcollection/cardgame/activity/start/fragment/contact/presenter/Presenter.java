package com.example.myboy.appcollection.cardgame.activity.start.fragment.contact.presenter;

import com.example.myboy.appcollection.cardgame.base.BasePresenter;
import com.example.myboy.appcollection.cardgame.bean.ContactBean;

import java.util.List;

public interface Presenter extends BasePresenter {

    void queryContacts();

    interface View{

        boolean isHavePermission();

        void setContactsData(List<ContactBean> data);

        void haveNoneContact();

        void notHavePermission();

    }

}
