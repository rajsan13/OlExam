package in.skaipal.kushalm.cuisinicuser.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import in.skaipal.kushalm.cuisinicuser.R;

public class TravelFragment extends Fragment {
    private static final String EXTRA_TEXT = "text";

    public static TravelFragment createFor(String str) {
        TravelFragment travelFragment = new TravelFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_TEXT, str);
        travelFragment.setArguments(bundle);
        return travelFragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_travel, viewGroup, false);
    }

    public void onViewCreated(View view, @Nullable Bundle bundle) {
        getArguments().getString(EXTRA_TEXT);
    }
}
