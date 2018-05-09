package com.mikolaj_app.stacjapogodowa;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;


public class PagerAdapter extends AppCompatActivity {

    public static ArrayList<Fragment> mFragments = new ArrayList<>();
    private String tabTitles[] = new String[]{"Zarządzanie", "Status", "Statystyki"};

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private Toolbar toolbar;

    public final String EXTRA_KEY_INSTALL_FRAG = "true or false for install frag";
    public boolean mInstallFrag = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager_adaper);

        if(savedInstanceState != null){
            mInstallFrag = savedInstanceState.getBoolean(EXTRA_KEY_INSTALL_FRAG);
        }

        if(mInstallFrag){
            installFragments();
        }

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mViewPager = findViewById(R.id.view_pager);
        mTabLayout = findViewById(R.id.tabLayout);

        FragmentManager manager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentPagerAdapter(manager){
        @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return tabTitles[position];
            }

        });

        mViewPager.setCurrentItem(1); // -> czyli MainFragment

        mTabLayout.setupWithViewPager(mViewPager);


    }//koniec onCreate

    public void installFragments(){
        mFragments.add(new ManagementFragment());
        mFragments.add(new MainFragment());
        mFragments.add(new StatisticsFragment());
        mInstallFrag = false;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(EXTRA_KEY_INSTALL_FRAG, mInstallFrag);
    }


}//koniec klasy
