package in.skaipal.kushalm.cuisinicuser.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import in.skaipal.kushalm.cuisinicuser.R;

public class ContactUsActivity extends AppCompatActivity {
    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_contct_us);
        getSupportActionBar().setTitle((CharSequence) "Contact Us");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((CardView) findViewById(R.id.call)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                view = new Intent("android.intent.action.DIAL");
                view.setData(Uri.parse("tel:9040286500"));
                ContactUsActivity.this.startActivity(view);
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        onBackPressed();
        return true;
    }
}
