package in.skaipal.kushalm.cuisinicuser.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import com.mindorks.placeholderview.PlaceHolderView;
import in.skaipal.kushalm.cuisinicuser.R;
import in.skaipal.kushalm.cuisinicuser.model.Image;
import in.skaipal.kushalm.cuisinicuser.model.ImageTypeBig;
import in.skaipal.kushalm.cuisinicuser.model.ImageTypeSmallList;
import in.skaipal.kushalm.cuisinicuser.model.Utils;
import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {
    String RestName;
    PlaceHolderView mGalleryView;

    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_rest_gallery);
        bundle = getIntent().getExtras();
        if (bundle != null) {
            this.RestName = bundle.getString("restName");
        }
        getSupportActionBar().setTitle(this.RestName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.mGalleryView = (PlaceHolderView) findViewById(R.id.galleryViewRest);
        setupGallery();
    }

    private void setupGallery() {
        List loadImages = Utils.loadImages(getApplicationContext());
        List arrayList = new ArrayList();
        int i = 0;
        while (true) {
            int i2 = 10;
            if (loadImages.size() <= 10) {
                i2 = loadImages.size();
            }
            if (i >= i2) {
                break;
            }
            arrayList.add(loadImages.get(i));
            i++;
        }
        this.mGalleryView.addView(new ImageTypeSmallList(getApplicationContext(), arrayList));
        for (int size = loadImages.size() - 1; size >= 0; size--) {
            this.mGalleryView.addView(new ImageTypeBig(getApplicationContext(), this.mGalleryView, ((Image) loadImages.get(size)).getImageUrl()));
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
