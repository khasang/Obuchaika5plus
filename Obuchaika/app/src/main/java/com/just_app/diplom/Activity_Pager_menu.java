package com.just_app.diplom;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;


public class Activity_Pager_menu extends FragmentActivity {
    private Subject mSubject= new Subject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String item = (String) getIntent()
                .getSerializableExtra(Fragment_menu.EXTRA_ITEM);
        final int count = mSubject.content.size();

        ViewPager mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);

        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                Content content=mSubject.content.get(position);
             //   return Fragment_menu.newInstance(item);
                return null;
            }

            @Override
            public int getCount() {
                return count;
            }
        });
    }
}
