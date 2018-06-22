package in.skaipal.kushalm.cuisinicuser.model.expand;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import in.skaipal.kushalm.cuisinicuser.R;
import in.skaipal.kushalm.cuisinicuser.model.expand.expandableRecyclerView.models.ExpandableGroup;
import in.skaipal.kushalm.cuisinicuser.model.expand.expandableRecyclerView.viewholders.GroupViewHolder;

public class GenreViewHolder extends GroupViewHolder {
    private String additional;
    private ImageView arrow;
    private TextView description;
    private TextView genreName;
    private ImageView icon;
    private String price;
    private String quantity;

    public GenreViewHolder(View view) {
        super(view);
        this.genreName = (TextView) view.findViewById(R.id.list_item_genre_name);
        this.description = (TextView) view.findViewById(R.id.list_item_genre_name_description);
        this.arrow = (ImageView) view.findViewById(R.id.list_item_genre_arrow);
    }

    public void setGenreTitle(ExpandableGroup expandableGroup) {
        if (expandableGroup instanceof Genre) {
            this.genreName.setText(expandableGroup.getTitle());
        }
        if (expandableGroup instanceof MultiCheckGenre) {
            this.genreName.setText(expandableGroup.getTitle());
            this.description.setText(expandableGroup.getDescription());
            this.price = expandableGroup.getPrice();
            this.quantity = expandableGroup.getQuantity();
            this.additional = expandableGroup.getAdditional();
        }
        if (expandableGroup instanceof SingleCheckGenre) {
            this.genreName.setText(expandableGroup.getTitle());
        }
    }

    public void expand() {
        animateExpand();
    }

    public void collapse() {
        animateCollapse();
    }

    private void animateExpand() {
        Animation rotateAnimation = new RotateAnimation(360.0f, 180.0f, 1, 0.5f, 1, 0.5f);
        rotateAnimation.setDuration(300);
        rotateAnimation.setFillAfter(true);
        this.arrow.setAnimation(rotateAnimation);
    }

    private void animateCollapse() {
        Animation rotateAnimation = new RotateAnimation(180.0f, 360.0f, 1, 0.5f, 1, 0.5f);
        rotateAnimation.setDuration(300);
        rotateAnimation.setFillAfter(true);
        this.arrow.setAnimation(rotateAnimation);
    }
}
