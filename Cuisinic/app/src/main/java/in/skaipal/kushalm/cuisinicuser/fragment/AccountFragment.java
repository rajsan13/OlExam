package in.skaipal.kushalm.cuisinicuser.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import in.skaipal.kushalm.cuisinicuser.R;
import in.skaipal.kushalm.cuisinicuser.activity.AddressActivity;
import in.skaipal.kushalm.cuisinicuser.activity.OrderActivity;
import in.skaipal.kushalm.cuisinicuser.activity.ProfileActivity;
import in.skaipal.kushalm.cuisinicuser.database.CartDatabase;

public class AccountFragment extends Fragment {
    private static final String EXTRA_TEXT = "text";
    int Count = 0;
    String IMAGE;
    String MAIL;
    String MOBILE;
    String NAME;
    TextView addressCount;
    CartDatabase db;
    TextView email;
    ImageView image;
    TextView name;
    ProgressBar progress;
    SQLiteDatabase sdb;

    public static AccountFragment createFor(String str) {
        AccountFragment accountFragment = new AccountFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_TEXT, str);
        accountFragment.setArguments(bundle);
        return accountFragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_account, viewGroup, false);
    }

    public void onViewCreated(View view, @Nullable Bundle bundle) {
        getArguments().getString(EXTRA_TEXT);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.profile);
        LinearLayout linearLayout2 = (LinearLayout) view.findViewById(R.id.addressCard);
        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.orderLayout);
        this.name = (TextView) view.findViewById(R.id.accountName);
        this.email = (TextView) view.findViewById(R.id.accountEmail);
        this.image = (ImageView) view.findViewById(R.id.accountImage);
        this.addressCount = (TextView) view.findViewById(R.id.addressCount);
        this.progress = (ProgressBar) view.findViewById(R.id.progress);
        this.db = new CartDatabase(getActivity());
        this.sdb = this.db.getWritableDatabase();
        try {
            Cursor rawQuery = this.sdb.rawQuery("select name, mail,mobile, image from user ", null);
            if (rawQuery.moveToFirst()) {
                do {
                    this.NAME = rawQuery.getString(0);
                    this.MAIL = rawQuery.getString(1);
                    this.MOBILE = rawQuery.getString(2);
                    this.IMAGE = rawQuery.getString(3);
                } while (rawQuery.moveToNext());
            }
            if (!this.NAME.equals("null")) {
                this.name.setText(this.NAME);
            }
            Glide.with(getActivity()).load(this.IMAGE).listener(new RequestListener<String, GlideDrawable>() {
                public boolean onException(Exception exception, String str, Target<GlideDrawable> target, boolean z) {
                    AccountFragment.this.progress.setVisibility(8);
                    return null;
                }

                public boolean onResourceReady(GlideDrawable glideDrawable, String str, Target<GlideDrawable> target, boolean z, boolean z2) {
                    AccountFragment.this.progress.setVisibility(8);
                    return null;
                }
            }).into(this.image);
            this.email.setText(this.MAIL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            view = this.sdb.rawQuery("Select count(*) from address", null);
            if (view.moveToFirst()) {
                do {
                    this.Count = view.getInt(0);
                } while (view.moveToNext());
            }
            view = this.addressCount;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Saved Address: ");
            stringBuilder.append(this.Count);
            view.setText(stringBuilder.toString());
        } catch (View view2) {
            view2.printStackTrace();
            this.Count = 0;
            view2 = this.addressCount;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Saved Address: ");
            stringBuilder2.append(this.Count);
            view2.setText(stringBuilder2.toString());
        }
        linearLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                view = new Intent(AccountFragment.this.getActivity(), ProfileActivity.class);
                view.putExtra("name", AccountFragment.this.NAME);
                view.putExtra("mail", AccountFragment.this.MAIL);
                view.putExtra("mobile", AccountFragment.this.MOBILE);
                view.putExtra("pic", AccountFragment.this.IMAGE);
                AccountFragment.this.startActivity(view);
            }
        });
        linearLayout2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                AccountFragment.this.startActivity(new Intent(AccountFragment.this.getActivity(), AddressActivity.class));
            }
        });
        relativeLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                AccountFragment.this.startActivity(new Intent(AccountFragment.this.getActivity(), OrderActivity.class));
            }
        });
    }

    public void onResume() {
        super.onResume();
        this.progress.setVisibility(0);
        try {
            Cursor rawQuery = this.sdb.rawQuery("select name, mail,mobile, image from user ", null);
            if (rawQuery.moveToFirst()) {
                do {
                    this.NAME = rawQuery.getString(0);
                    this.MAIL = rawQuery.getString(1);
                    this.MOBILE = rawQuery.getString(2);
                    this.IMAGE = rawQuery.getString(3);
                } while (rawQuery.moveToNext());
            }
            if (!this.NAME.equals("null")) {
                this.name.setText(this.NAME);
            }
            Glide.with(getActivity()).load(this.IMAGE).listener(new RequestListener<String, GlideDrawable>() {
                public boolean onException(Exception exception, String str, Target<GlideDrawable> target, boolean z) {
                    AccountFragment.this.progress.setVisibility(8);
                    return null;
                }

                public boolean onResourceReady(GlideDrawable glideDrawable, String str, Target<GlideDrawable> target, boolean z, boolean z2) {
                    AccountFragment.this.progress.setVisibility(8);
                    return null;
                }
            }).into(this.image);
            this.email.setText(this.MAIL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        TextView textView;
        try {
            Cursor rawQuery2 = this.sdb.rawQuery("Select count(*) from address", null);
            if (rawQuery2.moveToFirst()) {
                do {
                    this.Count = rawQuery2.getInt(0);
                } while (rawQuery2.moveToNext());
            }
            textView = this.addressCount;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Saved Address: ");
            stringBuilder.append(this.Count);
            textView.setText(stringBuilder.toString());
        } catch (Exception e2) {
            e2.printStackTrace();
            this.Count = 0;
            textView = this.addressCount;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Saved Address: ");
            stringBuilder2.append(this.Count);
            textView.setText(stringBuilder2.toString());
        }
    }
}
