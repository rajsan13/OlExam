package in.skaipal.kushalm.cuisinicuser.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.media.ExifInterface;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import in.skaipal.kushalm.cuisinicuser.R;
import in.skaipal.kushalm.cuisinicuser.model.LiveMusicOther;
import java.util.ArrayList;

public class LiveBookingAdapter extends Adapter<MyViewHolder> {
    public static final String KEY_MAIL = "mail";
    private static final String PREFER_NAME = "SessionSharedPreference";
    private String Email;
    private ArrayList<LiveMusicOther> contest;
    private Context mContext;
    private SharedPreferences sp;

    public class MyViewHolder extends ViewHolder {
        public TextView date;
        public TextView eTime;
        public TextView id;
        public TextView reason;
        public TextView sTime;
        public TextView status;

        public MyViewHolder(View view) {
            super(view);
            this.id = (TextView) view.findViewById(R.id.bookingId);
            this.date = (TextView) view.findViewById(R.id.bookingDate);
            this.sTime = (TextView) view.findViewById(R.id.bookingStartTime);
            this.eTime = (TextView) view.findViewById(R.id.bookingEndTime);
            this.status = (TextView) view.findViewById(R.id.bookingStatus);
            this.reason = (TextView) view.findViewById(R.id.declineReason);
            LiveBookingAdapter.this.sp = LiveBookingAdapter.this.mContext.getSharedPreferences(LiveBookingAdapter.PREFER_NAME, 0);
            LiveBookingAdapter.this.Email = LiveBookingAdapter.this.sp.getString("mail", null);
        }
    }

    public LiveBookingAdapter(Context context, ArrayList<LiveMusicOther> arrayList) {
        this.mContext = context;
        this.contest = arrayList;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_live_music, viewGroup, false));
    }

    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        TextView textView = myViewHolder.id;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Id: ");
        stringBuilder.append(((LiveMusicOther) this.contest.get(i)).getBookingId());
        textView.setText(stringBuilder.toString());
        textView = myViewHolder.date;
        stringBuilder = new StringBuilder();
        stringBuilder.append("Date: ");
        stringBuilder.append(((LiveMusicOther) this.contest.get(i)).getDate());
        textView.setText(stringBuilder.toString());
        textView = myViewHolder.sTime;
        stringBuilder = new StringBuilder();
        stringBuilder.append("Start Time: ");
        stringBuilder.append(((LiveMusicOther) this.contest.get(i)).getStartTime());
        textView.setText(stringBuilder.toString());
        textView = myViewHolder.eTime;
        stringBuilder = new StringBuilder();
        stringBuilder.append("End Time: ");
        stringBuilder.append(((LiveMusicOther) this.contest.get(i)).getEndTime());
        textView.setText(stringBuilder.toString());
        if (((LiveMusicOther) this.contest.get(i)).getStatus().equalsIgnoreCase(ExifInterface.GPS_MEASUREMENT_2D)) {
            myViewHolder.status.setText("Pending");
        } else if (((LiveMusicOther) this.contest.get(i)).getStatus().equalsIgnoreCase("1")) {
            myViewHolder.status.setText("Accepted");
        } else if (((LiveMusicOther) this.contest.get(i)).getStatus().equalsIgnoreCase("0")) {
            myViewHolder.status.setText("Rejected");
            myViewHolder.reason.setVisibility(0);
        }
        if (!((LiveMusicOther) this.contest.get(i)).getReason().equalsIgnoreCase("nill")) {
            myViewHolder.reason.setText(((LiveMusicOther) this.contest.get(i)).getReason());
            myViewHolder.reason.setVisibility(0);
        }
    }

    public int getItemCount() {
        return this.contest.size();
    }
}
