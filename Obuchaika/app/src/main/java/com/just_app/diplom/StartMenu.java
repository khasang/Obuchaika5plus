package com.just_app.diplom;


import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;

public class StartMenu extends SingleFragmentActivity {
    DialogFragment dlg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dlg= new SelectDialog();
    }

    @Override
    protected Fragment createFragment() {
        return new StartFragment();
    }

    @Override
    public void onBackPressed() {
        dlg.show(getFragmentManager(), "dlg");
    }
}
