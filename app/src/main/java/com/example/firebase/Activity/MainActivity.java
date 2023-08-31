package com.example.firebase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.firebase.Activity.DashboardActivity;
import com.example.firebase.Adapter.LoginAdapter;
import com.google.android.material.tabs.TabLayout;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    TabLayout tl_tab_layout;
    ViewPager vp_view_pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        tl_tab_layout = findViewById(R.id.tl_tab_layout);
        vp_view_pager = findViewById(R.id.vp_view_pager);

        tl_tab_layout.addTab(tl_tab_layout.newTab().setText("Phone Number"));
        tl_tab_layout.addTab(tl_tab_layout.newTab().setText("Email Id"));

        tl_tab_layout.setTabGravity(TabLayout.GRAVITY_FILL);

        vp_view_pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tl_tab_layout));

        tl_tab_layout.addOnTabSelectedListener( new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vp_view_pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        LoginAdapter loginAdapter = new LoginAdapter(getSupportFragmentManager(),this,tl_tab_layout.getTabCount());
        vp_view_pager.setAdapter(loginAdapter);

    }
}