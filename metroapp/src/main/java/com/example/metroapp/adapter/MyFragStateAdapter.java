package com.example.metroapp.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class MyFragStateAdapter extends FragmentStatePagerAdapter {

    //FragmentPagerAdapter
    private List<Fragment> mFragmentList;

    public MyFragStateAdapter(@NonNull FragmentManager fm,List<Fragment> fragmentList) {
        super(fm);
        this.mFragmentList =fragmentList;

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragmentList == null ? null : mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList == null ? 0 : mFragmentList.size();
    }
}
//
//public class ViewPagerAdapter extends PagerAdapter {
//
//    /**
//     * 已经没有用的测试代码
//     * */
//    @Override
//    public int getCount() {
//        return 0;
//    }
//
//    @Override
//    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
//        return false;
//    }
//
//    @NonNull
//    @Override
//    public Object instantiateItem(@NonNull ViewGroup container, int position) {
//        return super.instantiateItem(container, position);
//    }
//
//    @Override
//    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);
//    }
//}

