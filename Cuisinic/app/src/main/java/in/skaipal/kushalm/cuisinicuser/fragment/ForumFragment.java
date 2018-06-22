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
import in.skaipal.kushalm.cuisinicuser.activity.ShareRecipeActivity;

public class ForumFragment extends Fragment {
    private static final String EXTRA_TEXT = "text";

    public static ForumFragment createFor(String str) {
        ForumFragment forumFragment = new ForumFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_TEXT, str);
        forumFragment.setArguments(bundle);
        return forumFragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_forum, viewGroup, false);
    }

    public void onViewCreated(View view, @Nullable Bundle bundle) {
        ((CardView) view.findViewById(R.id.shareRecipe)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ForumFragment.this.startActivity(new Intent(ForumFragment.this.getActivity(), ShareRecipeActivity.class));
            }
        });
    }
}
