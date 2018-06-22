package in.skaipal.kushalm.cuisinicuser.model.transform;

import android.view.View;
import com.yarolegovich.slidingrootnav.transform.RootTransformation;
import com.yarolegovich.slidingrootnav.util.SideNavUtils;

public class ScaleTransformation implements RootTransformation {
    private static final float START_SCALE = 1.0f;
    private final float endScale;

    public ScaleTransformation(float f) {
        this.endScale = f;
    }

    public void transform(float f, View view) {
        f = SideNavUtils.evaluate(f, 1.0f, this.endScale);
        view.setScaleX(f);
        view.setScaleY(f);
    }
}
