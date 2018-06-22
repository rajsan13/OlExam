package in.skaipal.kushalm.cuisinicuser.model.transform;

import android.view.View;
import com.yarolegovich.slidingrootnav.transform.RootTransformation;
import java.util.List;

public class CompositeTransformation implements RootTransformation {
    private List<RootTransformation> transformations;

    public CompositeTransformation(List<RootTransformation> list) {
        this.transformations = list;
    }

    public void transform(float f, View view) {
        for (RootTransformation transform : this.transformations) {
            transform.transform(f, view);
        }
    }
}
