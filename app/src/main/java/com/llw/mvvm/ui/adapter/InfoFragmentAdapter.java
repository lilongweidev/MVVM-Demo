package com.llw.mvvm.ui.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Fragment适配器
 * @author llw
 */
public class InfoFragmentAdapter extends FragmentPagerAdapter {

    String titleArr[];
    List<Fragment> mFragmentList;

    public InfoFragmentAdapter(FragmentManager fm, List<Fragment> list, String[] titleArr) {
        super(fm);
        mFragmentList = list;
        this.titleArr = titleArr;
    }

    @Override
    public Fragment getItem(int i) {
        return mFragmentList.get(i);
    }

    @Override
    public int getCount() {
        return mFragmentList != null ? mFragmentList.size() : 0;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleArr[position];
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);
    }

}
