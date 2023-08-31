package com.example.firebase.Adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.firebase.Fragment.EmailLoginFragment;
import com.example.firebase.Fragment.PhoneNumberLoginFragment;

public class LoginAdapter extends FragmentPagerAdapter {

    Context context;
    int totalTabs;

    public LoginAdapter(FragmentManager fragmentManager,Context context,int totalTabs){

        super(fragmentManager);

        this.context = context;
        this.totalTabs = totalTabs;

    }

    public int getCount(){

        return totalTabs;
    }

    public Fragment getItem(int position){

        switch (position){

            case 0:

                PhoneNumberLoginFragment phoneNubmberLoginFragment = new PhoneNumberLoginFragment();
                return phoneNubmberLoginFragment;

            case 1:

                EmailLoginFragment emailLoginFragment = new EmailLoginFragment();
                return emailLoginFragment;

            default:

                return null;
        }
    }

}
