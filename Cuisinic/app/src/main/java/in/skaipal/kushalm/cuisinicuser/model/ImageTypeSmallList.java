package in.skaipal.kushalm.cuisinicuser.model;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.annotations.Animate;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import java.util.List;

@Animate(19)
@Layout(2131427462)
@NonReusable
public class ImageTypeSmallList {
    private Context mContext;
    private List<Image> mImageList;
    @View(2131296580)
    private PlaceHolderView mPlaceHolderView;

    public ImageTypeSmallList(Context context, List<Image> list) {
        this.mContext = context;
        this.mImageList = list;
    }

    @Resolve
    private void onResolved() {
        this.mPlaceHolderView.getBuilder().setHasFixedSize(false).setItemViewCacheSize(10).setLayoutManager(new LinearLayoutManager(this.mContext, 0, false));
        for (Image imageUrl : this.mImageList) {
            this.mPlaceHolderView.addView(new ImageTypeSmall(this.mContext, this.mPlaceHolderView, imageUrl.getImageUrl()));
        }
    }
}
