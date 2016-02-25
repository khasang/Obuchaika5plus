package com.just_app.diplom;

import android.app.Fragment;

public class Activity_menu extends SingleFragmentActivity{


    @Override
    protected Fragment createFragment() {

        String item= (String)getIntent()
                .getSerializableExtra(Fragment_menu.EXTRA_ITEM);
        return Fragment_menu.newInstance(item);
    }
}


