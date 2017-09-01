package com.example.android.miwok;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by lee on 9/1/2017.
 */

public class MiwokFragmentPageAdapter extends FragmentPagerAdapter {



    public MiwokFragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new NumbersFragment();
            case 1:
                return new FamilyFragment();
            case 2:
                return new ColorsFragment();
            case 3:
                return new PhrasesFragment();
            default:
                return null;
        }

    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Numbers";
            case 1:
                return "Family";
            case 2:
                return "Colors";
            case 3:
                return "Phrases";
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
