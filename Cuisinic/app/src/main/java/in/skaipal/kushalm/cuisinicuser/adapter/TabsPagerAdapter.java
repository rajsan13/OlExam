package in.skaipal.kushalm.cuisinicuser.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import in.skaipal.kushalm.cuisinicuser.fragment.CancelledOrderFragment;
import in.skaipal.kushalm.cuisinicuser.fragment.CompletedOrderFragment;
import in.skaipal.kushalm.cuisinicuser.fragment.ProcessedOrderFragment;
import in.skaipal.kushalm.cuisinicuser.fragment.openOrderFragment;

public class TabsPagerAdapter extends FragmentStatePagerAdapter {
    int num;

    public TabsPagerAdapter(FragmentManager fragmentManager, int i) {
        super(fragmentManager);
        this.num = i;
    }

    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new openOrderFragment();
            case 1:
                return new ProcessedOrderFragment();
            case 2:
                return new CompletedOrderFragment();
            case 3:
                return new CancelledOrderFragment();
            default:
                return 0;
        }
    }

    public int getCount() {
        return this.num;
    }
}
