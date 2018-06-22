package in.skaipal.kushalm.cuisinicuser.adapter;

import android.content.Context;
import android.os.Build.VERSION;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import in.skaipal.kushalm.cuisinicuser.R;

public class ViewPagerAdapter extends PagerAdapter {
    private Context mContext;
    private int[] mResources;

    public ViewPagerAdapter(Context context, int[] iArr) {
        this.mContext = context;
        this.mResources = iArr;
    }

    public int getCount() {
        return this.mResources.length;
    }

    public boolean isViewFromObject(View view, Object obj) {
        return view == ((LinearLayout) obj) ? true : null;
    }

    public Object instantiateItem(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.pager_item, viewGroup, false);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.img_pager_item);
        if (VERSION.SDK_INT >= 23) {
            imageView.setImageResource(this.mResources[i]);
        } else {
            imageView.setImageDrawable(this.mContext.getResources().getDrawable(this.mResources[i]));
        }
        imageView.setScaleType(ScaleType.FIT_XY);
        viewGroup.addView(inflate);
        return inflate;
    }

    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        viewGroup.removeView((LinearLayout) obj);
    }
}
