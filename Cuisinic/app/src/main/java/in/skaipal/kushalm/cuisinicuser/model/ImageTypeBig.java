package in.skaipal.kushalm.cuisinicuser.model;

import android.content.Context;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.annotations.Animate;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

@Animate(1)
@Layout(2131427463)
@NonReusable
public class ImageTypeBig {
    @View(2131296473)
    private ImageView imageView;
    private Context mContext;
    private PlaceHolderView mPlaceHolderView;
    private String mUlr;
    @View(2131296589)
    private ProgressBar progress;

    public ImageTypeBig(Context context, PlaceHolderView placeHolderView, String str) {
        this.mContext = context;
        this.mPlaceHolderView = placeHolderView;
        this.mUlr = str;
    }

    @Resolve
    private void onResolved() {
        Glide.with(this.mContext).load(this.mUlr).listener(new RequestListener<String, GlideDrawable>() {
            public boolean onException(Exception exception, String str, Target<GlideDrawable> target, boolean z) {
                ImageTypeBig.this.progress.setVisibility(8);
                return null;
            }

            public boolean onResourceReady(GlideDrawable glideDrawable, String str, Target<GlideDrawable> target, boolean z, boolean z2) {
                ImageTypeBig.this.progress.setVisibility(8);
                return null;
            }
        }).into(this.imageView);
    }
}
