package in.skaipal.kushalm.cuisinicuser.model.expand.expandableCheckRecyclerView.listeners;

import android.view.View;
import in.skaipal.kushalm.cuisinicuser.model.expand.expandableCheckRecyclerView.models.CheckedExpandableGroup;

public interface OnCheckChildClickListener {
    void onCheckChildCLick(View view, boolean z, CheckedExpandableGroup checkedExpandableGroup, int i);
}
