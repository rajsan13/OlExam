package in.skaipal.kushalm.cuisinicuser.model.transform;

import android.view.View;
import com.yarolegovich.slidingrootnav.transform.RootTransformation;
import com.yarolegovich.slidingrootnav.util.SideNavUtils;

public class YTranslationTransformation implements RootTransformation {
    private static final float START_TRANSLATION = 0.0f;
    private final float endTranslation;

    public YTranslationTransformation(float f) {
        this.endTranslation = f;
    }

    public void transform(float f, View view) {
        view.setTranslationY(SideNavUtils.evaluate(f, 0.0f, this.endTranslation));
    }
}
