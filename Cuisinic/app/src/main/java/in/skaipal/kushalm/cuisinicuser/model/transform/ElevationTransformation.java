package in.skaipal.kushalm.cuisinicuser.model.transform;

import android.os.Build.VERSION;
import android.view.View;
import com.yarolegovich.slidingrootnav.transform.RootTransformation;
import com.yarolegovich.slidingrootnav.util.SideNavUtils;

public class ElevationTransformation implements RootTransformation {
    private static final float START_ELEVATION = 0.0f;
    private final float endElevation;

    public ElevationTransformation(float f) {
        this.endElevation = f;
    }

    public void transform(float f, View view) {
        if (VERSION.SDK_INT >= 21) {
            view.setElevation(SideNavUtils.evaluate(f, 0.0f, this.endElevation));
        }
    }
}
