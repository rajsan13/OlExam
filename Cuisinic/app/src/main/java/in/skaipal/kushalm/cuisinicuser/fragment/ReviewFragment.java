package in.skaipal.kushalm.cuisinicuser.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import in.skaipal.kushalm.cuisinicuser.R;
import in.skaipal.kushalm.cuisinicuser.activity.ContestActivity;
import in.skaipal.kushalm.cuisinicuser.activity.LiveMusicActivity;
import in.skaipal.kushalm.cuisinicuser.activity.OfferActivity;

public class ReviewFragment extends Fragment {
    private static final String EXTRA_TEXT = "text";

    public static ReviewFragment createFor(String str) {
        ReviewFragment reviewFragment = new ReviewFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_TEXT, str);
        reviewFragment.setArguments(bundle);
        return reviewFragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_review, viewGroup, false);
    }

    public void onViewCreated(View view, @Nullable Bundle bundle) {
        CardView cardView = (CardView) view.findViewById(R.id.contestCard);
        CardView cardView2 = (CardView) view.findViewById(R.id.liveMusicCard);
        CardView cardView3 = (CardView) view.findViewById(R.id.offerCard);
        cardView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ReviewFragment.this.startActivity(new Intent(ReviewFragment.this.getActivity(), ContestActivity.class));
            }
        });
        cardView2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ReviewFragment.this.startActivity(new Intent(ReviewFragment.this.getActivity(), LiveMusicActivity.class));
            }
        });
        cardView3.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ReviewFragment.this.startActivity(new Intent(ReviewFragment.this.getActivity(), OfferActivity.class));
            }
        });
    }
}
