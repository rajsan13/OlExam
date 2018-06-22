package in.skaipal.kushalm.cuisinicuser.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.Switch;
import in.skaipal.kushalm.cuisinicuser.R;
import in.skaipal.kushalm.cuisinicuser.service.MyService;

@RequiresApi(api = 23)
public class SettingFragment extends Fragment {
    private static final String EXTRA_TEXT = "text";
    private static final String PREFERENCE = "service";
    int[] colors;
    int[] colors1;
    boolean running = true;
    Switch service;
    SharedPreferences sp;
    int[][] states;
    int[][] states1;
    LinearLayout switchLayout;

    public SettingFragment() {
        r2 = new int[2][];
        r2[0] = new int[]{16842910};
        r2[1] = new int[]{-16842910};
        this.states = r2;
        this.colors = new int[]{Color.rgb(136, 164, 64), -7829368};
        r2 = new int[2][];
        r2[0] = new int[]{16842910};
        r2[1] = new int[]{-16842910};
        this.states1 = r2;
        this.colors1 = new int[]{-7829368, Color.rgb(136, 164, 64)};
    }

    public static SettingFragment createFor(String str) {
        SettingFragment settingFragment = new SettingFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_TEXT, str);
        settingFragment.setArguments(bundle);
        return settingFragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_setting, viewGroup, false);
    }

    @RequiresApi(api = 23)
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        getArguments().getString(EXTRA_TEXT);
        this.switchLayout = (LinearLayout) view.findViewById(R.id.notificationLayout);
        this.service = (Switch) view.findViewById(R.id.notificationSwitch);
        this.sp = getActivity().getSharedPreferences("service", 0);
        if (this.sp.getBoolean("serviceRunning", true) != null) {
            this.service.setChecked(true);
        } else {
            this.service.setChecked(false);
        }
        this.switchLayout.setOnClickListener(new OnClickListener() {
            @RequiresApi(api = 23)
            public void onClick(View view) {
                if (SettingFragment.this.running != null) {
                    SettingFragment.this.service.setChecked(false);
                    SettingFragment.this.running = false;
                    SettingFragment.this.stopService();
                    return;
                }
                SettingFragment.this.service.setChecked(true);
                SettingFragment.this.running = true;
                SettingFragment.this.startService();
            }
        });
        this.service.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (z) {
                    SettingFragment.this.running = true;
                    SettingFragment.this.startService();
                    return;
                }
                SettingFragment.this.running = false;
                SettingFragment.this.stopService();
            }
        });
    }

    private void stopService() {
        getActivity().stopService(new Intent(getActivity(), MyService.class));
    }

    @RequiresApi(api = 23)
    public void startService() {
        getActivity().startService(new Intent(getActivity(), MyService.class));
    }
}
