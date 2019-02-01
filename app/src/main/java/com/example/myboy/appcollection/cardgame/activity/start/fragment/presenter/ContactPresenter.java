package com.example.myboy.appcollection.cardgame.activity.start.fragment.presenter;

import android.database.Cursor;
import android.provider.ContactsContract;

import com.example.myboy.appcollection.cardgame.activity.start.fragment.ContactFragment;
import com.example.myboy.appcollection.cardgame.bean.ContactBean;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;

public class ContactPresenter implements Presenter {

    View view;
    List<ContactBean> contacts;
    public ContactPresenter(View view) {
        this.view = view;
        contacts = new ArrayList<>();
        ContactFragment fragment = (ContactFragment) view;
        if(fragment.isHavePermission()){
            view.notHavePermission();
        }else{

        }
    }

    @Override
    public void queryContacts() {
        Fragment fragment = (Fragment) view;
        Cursor cursor = fragment.getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
        while(cursor.moveToNext()){
            ContactBean contactBean = new ContactBean();
            contactBean.setName(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
            contactBean.setPhone(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
            contacts.add(contactBean);
        }
        if(contacts!=null&&contacts.size()>0){
            view.setContactsData(contacts);
        }else{
            view.haveNoneContact();
        }
        cursor.close();
    }
}
