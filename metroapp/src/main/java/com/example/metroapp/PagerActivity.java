package com.example.metroapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.metroapp.adapter.MyFragStateAdapter;
import com.example.metroapp.fragment.frag1.SearchFragment;
import com.example.metroapp.fragment.frag2.LeftListFragment;
import com.example.metroapp.fragment.frag3.MapFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class PagerActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private BottomNavigationView mBottomNavigationView;
    private MyFragStateAdapter FragStateAdapter;
    private List<Fragment> mFragmentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);

        mViewPager = findViewById(R.id.vp2);
        mBottomNavigationView = findViewById(R.id.bottom_menu);
        
//        initData();
        FragStateAdapter = new MyFragStateAdapter(getSupportFragmentManager(),mFragmentList);
        mViewPager.setAdapter(FragStateAdapter);

       mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(R.id.menu_1 == item.getItemId()) {
                    mViewPager.setCurrentItem(0);
                }else if(R.id.menu_2 == item.getItemId()) {

                    mViewPager.setCurrentItem(1);
                }
                    else if(R.id.menu_3 == item.getItemId()) {
                    mViewPager.setCurrentItem(2);
                }
                return true;
            }
        });

       mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
           @Override
           public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

           }

           @Override
           public void onPageSelected(int position) {
               switch (position){
                   case 0:
                       mBottomNavigationView.setSelectedItemId(R.id.menu_1);
                       break;
                   case 1:
                       mBottomNavigationView.setSelectedItemId(R.id.menu_2);
                       break;
                   case 2:
                       mBottomNavigationView.removeBadge(R.id.menu_3);
                       mBottomNavigationView.setSelectedItemId(R.id.menu_3);
                       break;
                   default:
                       break;
               }
           }

           @Override
           public void onPageScrollStateChanged(int state) {

           }
       });


    }

    private void initData() {
        mFragmentList = new ArrayList<>();

        SearchFragment fragment1 = SearchFragment.newInstance("","");
        LeftListFragment fragment2 = LeftListFragment.newInstance("","");
        MapFragment fragment3 = MapFragment.newInstance("","");

        mFragmentList.add(fragment1);
        mFragmentList.add(fragment2);
        mFragmentList.add(fragment3);
    }
}