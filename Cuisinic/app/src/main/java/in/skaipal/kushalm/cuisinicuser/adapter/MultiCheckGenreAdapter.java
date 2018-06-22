package in.skaipal.kushalm.cuisinicuser.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import in.skaipal.kushalm.cuisinicuser.R;
import in.skaipal.kushalm.cuisinicuser.activity.MakeSaladActivity;
import in.skaipal.kushalm.cuisinicuser.model.MultiCheckArtistViewHolder;
import in.skaipal.kushalm.cuisinicuser.model.expand.Artist;
import in.skaipal.kushalm.cuisinicuser.model.expand.GenreViewHolder;
import in.skaipal.kushalm.cuisinicuser.model.expand.MultiCheckGenre;
import in.skaipal.kushalm.cuisinicuser.model.expand.expandableCheckRecyclerView.CheckableChildRecyclerViewAdapter;
import in.skaipal.kushalm.cuisinicuser.model.expand.expandableCheckRecyclerView.listeners.OnChildCheckChangedListener;
import in.skaipal.kushalm.cuisinicuser.model.expand.expandableCheckRecyclerView.models.CheckedExpandableGroup;
import in.skaipal.kushalm.cuisinicuser.model.expand.expandableRecyclerView.models.ExpandableGroup;
import java.util.ArrayList;

public class MultiCheckGenreAdapter extends CheckableChildRecyclerViewAdapter<GenreViewHolder, MultiCheckArtistViewHolder> {
    int[] TOTAL;
    Context mContext;

    public MultiCheckGenreAdapter(ArrayList<MultiCheckGenre> arrayList, Context context) {
        super(arrayList);
        this.mContext = context;
        this.TOTAL = new int[arrayList.size()];
        for (context = null; context < this.TOTAL.length; context++) {
            this.TOTAL[context] = 0;
        }
    }

    public MultiCheckArtistViewHolder onCreateCheckChildViewHolder(ViewGroup viewGroup, int i) {
        return new MultiCheckArtistViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_multicheck_artist, viewGroup, false));
    }

    public void onBindCheckChildViewHolder(final MultiCheckArtistViewHolder multiCheckArtistViewHolder, int i, final CheckedExpandableGroup checkedExpandableGroup, final int i2) {
        multiCheckArtistViewHolder.setArtistName(((Artist) checkedExpandableGroup.getItems().get(i2)).getName());
        multiCheckArtistViewHolder.setOnChildCheckedListener(new OnChildCheckChangedListener() {
            public void onChildCheckChanged(View view, boolean z, int i) {
                int i2;
                int i3;
                Button button;
                StringBuilder stringBuilder;
                if (!z) {
                    checkedExpandableGroup.unCheckChild(i2);
                    for (z = false; z < MakeSaladActivity.CRITERIA.size(); z++) {
                        if (((String) MakeSaladActivity.CRITERIA.get(z)).equals(checkedExpandableGroup.getTitle())) {
                            if (checkedExpandableGroup.getCheckedCount() <= Integer.parseInt(checkedExpandableGroup.getQuantity()) && checkedExpandableGroup.getCheckedCount() > 0) {
                                MultiCheckGenreAdapter.this.TOTAL[z] = Integer.parseInt(checkedExpandableGroup.getPrice());
                                i2 = 0;
                                i3 = i2;
                                while (i2 < MultiCheckGenreAdapter.this.TOTAL.length) {
                                    i3 += MultiCheckGenreAdapter.this.TOTAL[i2];
                                    i2++;
                                }
                                if (i3 != 0) {
                                    button = MakeSaladActivity.Cart;
                                    stringBuilder = new StringBuilder();
                                    stringBuilder.append("Add- ");
                                    stringBuilder.append(MultiCheckGenreAdapter.this.mContext.getResources().getString(R.string.Rupee));
                                    stringBuilder.append(" ");
                                    stringBuilder.append(i3);
                                    button.setText(stringBuilder.toString());
                                } else {
                                    MakeSaladActivity.Cart.setText("Add");
                                }
                            } else if (checkedExpandableGroup.getCheckedCount() > Integer.parseInt(checkedExpandableGroup.getQuantity())) {
                                MultiCheckGenreAdapter.this.TOTAL[z] = Integer.parseInt(checkedExpandableGroup.getPrice()) + ((checkedExpandableGroup.getCheckedCount() - Integer.parseInt(checkedExpandableGroup.getQuantity())) * Integer.parseInt(checkedExpandableGroup.getAdditional()));
                                i2 = 0;
                                i3 = i2;
                                while (i2 < MultiCheckGenreAdapter.this.TOTAL.length) {
                                    i3 += MultiCheckGenreAdapter.this.TOTAL[i2];
                                    i2++;
                                }
                                if (i3 != 0) {
                                    button = MakeSaladActivity.Cart;
                                    stringBuilder = new StringBuilder();
                                    stringBuilder.append("Add- ");
                                    stringBuilder.append(MultiCheckGenreAdapter.this.mContext.getResources().getString(R.string.Rupee));
                                    stringBuilder.append(" ");
                                    stringBuilder.append(i3);
                                    button.setText(stringBuilder.toString());
                                } else {
                                    MakeSaladActivity.Cart.setText("Add");
                                }
                            } else if (checkedExpandableGroup.getCheckedCount() == 0) {
                                MultiCheckGenreAdapter.this.TOTAL[z] = 0;
                                i2 = 0;
                                i3 = i2;
                                while (i2 < MultiCheckGenreAdapter.this.TOTAL.length) {
                                    i3 += MultiCheckGenreAdapter.this.TOTAL[i2];
                                    i2++;
                                }
                                if (i3 != 0) {
                                    button = MakeSaladActivity.Cart;
                                    stringBuilder = new StringBuilder();
                                    stringBuilder.append("Add- ");
                                    stringBuilder.append(MultiCheckGenreAdapter.this.mContext.getResources().getString(R.string.Rupee));
                                    stringBuilder.append(" ");
                                    stringBuilder.append(i3);
                                    button.setText(stringBuilder.toString());
                                } else {
                                    MakeSaladActivity.Cart.setText("Add");
                                }
                            }
                        }
                    }
                } else if (checkedExpandableGroup.getQuantity().equals("1") || checkedExpandableGroup.getAdditional().equals("0")) {
                    checkedExpandableGroup.checkChild(i2);
                    if (checkedExpandableGroup.getCheckedCount() > Integer.parseInt(checkedExpandableGroup.getQuantity())) {
                        multiCheckArtistViewHolder.setChecked(false);
                        checkedExpandableGroup.unCheckChild(i2);
                        view = MultiCheckGenreAdapter.this.mContext;
                        z = new StringBuilder();
                        z.append("Sorry only ");
                        z.append(checkedExpandableGroup.getQuantity());
                        z.append(" is allowed");
                        Toast.makeText(view, z.toString(), 0).show();
                        return;
                    }
                    for (z = false; z < MakeSaladActivity.CRITERIA.size(); z++) {
                        if (((String) MakeSaladActivity.CRITERIA.get(z)).equals(checkedExpandableGroup.getTitle())) {
                            if (checkedExpandableGroup.getCheckedCount() <= Integer.parseInt(checkedExpandableGroup.getQuantity())) {
                                MultiCheckGenreAdapter.this.TOTAL[z] = Integer.parseInt(checkedExpandableGroup.getPrice());
                                i2 = 0;
                                i3 = i2;
                                while (i2 < MultiCheckGenreAdapter.this.TOTAL.length) {
                                    i3 += MultiCheckGenreAdapter.this.TOTAL[i2];
                                    i2++;
                                }
                                if (i3 != 0) {
                                    button = MakeSaladActivity.Cart;
                                    stringBuilder = new StringBuilder();
                                    stringBuilder.append("Add- ");
                                    stringBuilder.append(MultiCheckGenreAdapter.this.mContext.getResources().getString(R.string.Rupee));
                                    stringBuilder.append(" ");
                                    stringBuilder.append(i3);
                                    button.setText(stringBuilder.toString());
                                } else {
                                    MakeSaladActivity.Cart.setText("Add");
                                }
                            } else if (checkedExpandableGroup.getCheckedCount() > Integer.parseInt(checkedExpandableGroup.getQuantity())) {
                                MultiCheckGenreAdapter.this.TOTAL[z] = Integer.parseInt(checkedExpandableGroup.getPrice()) + ((checkedExpandableGroup.getCheckedCount() - Integer.parseInt(checkedExpandableGroup.getQuantity())) * Integer.parseInt(checkedExpandableGroup.getAdditional()));
                                i2 = 0;
                                i3 = i2;
                                while (i2 < MultiCheckGenreAdapter.this.TOTAL.length) {
                                    i3 += MultiCheckGenreAdapter.this.TOTAL[i2];
                                    i2++;
                                }
                                if (i3 != 0) {
                                    button = MakeSaladActivity.Cart;
                                    stringBuilder = new StringBuilder();
                                    stringBuilder.append("Add- ");
                                    stringBuilder.append(MultiCheckGenreAdapter.this.mContext.getResources().getString(R.string.Rupee));
                                    stringBuilder.append(" ");
                                    stringBuilder.append(i3);
                                    button.setText(stringBuilder.toString());
                                } else {
                                    MakeSaladActivity.Cart.setText("Add");
                                }
                            }
                        }
                    }
                } else {
                    checkedExpandableGroup.checkChild(i2);
                    for (z = false; z < MakeSaladActivity.CRITERIA.size(); z++) {
                        if (((String) MakeSaladActivity.CRITERIA.get(z)).equals(checkedExpandableGroup.getTitle())) {
                            if (checkedExpandableGroup.getCheckedCount() <= Integer.parseInt(checkedExpandableGroup.getQuantity())) {
                                MultiCheckGenreAdapter.this.TOTAL[z] = Integer.parseInt(checkedExpandableGroup.getPrice());
                                i2 = 0;
                                i3 = i2;
                                while (i2 < MultiCheckGenreAdapter.this.TOTAL.length) {
                                    i3 += MultiCheckGenreAdapter.this.TOTAL[i2];
                                    i2++;
                                }
                                if (i3 != 0) {
                                    button = MakeSaladActivity.Cart;
                                    stringBuilder = new StringBuilder();
                                    stringBuilder.append("Add- ");
                                    stringBuilder.append(MultiCheckGenreAdapter.this.mContext.getResources().getString(R.string.Rupee));
                                    stringBuilder.append(" ");
                                    stringBuilder.append(i3);
                                    button.setText(stringBuilder.toString());
                                } else {
                                    MakeSaladActivity.Cart.setText("Add");
                                }
                            } else if (checkedExpandableGroup.getCheckedCount() > Integer.parseInt(checkedExpandableGroup.getQuantity())) {
                                MultiCheckGenreAdapter.this.TOTAL[z] = Integer.parseInt(checkedExpandableGroup.getPrice()) + ((checkedExpandableGroup.getCheckedCount() - Integer.parseInt(checkedExpandableGroup.getQuantity())) * Integer.parseInt(checkedExpandableGroup.getAdditional()));
                                i2 = 0;
                                i3 = i2;
                                while (i2 < MultiCheckGenreAdapter.this.TOTAL.length) {
                                    i3 += MultiCheckGenreAdapter.this.TOTAL[i2];
                                    i2++;
                                }
                                if (i3 != 0) {
                                    button = MakeSaladActivity.Cart;
                                    stringBuilder = new StringBuilder();
                                    stringBuilder.append("Add- ");
                                    stringBuilder.append(MultiCheckGenreAdapter.this.mContext.getResources().getString(R.string.Rupee));
                                    stringBuilder.append(" ");
                                    stringBuilder.append(i3);
                                    button.setText(stringBuilder.toString());
                                } else {
                                    MakeSaladActivity.Cart.setText("Add");
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    public GenreViewHolder onCreateGroupViewHolder(ViewGroup viewGroup, int i) {
        return new GenreViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_genre, viewGroup, false));
    }

    public void onBindGroupViewHolder(GenreViewHolder genreViewHolder, int i, ExpandableGroup expandableGroup) {
        genreViewHolder.setGenreTitle(expandableGroup);
    }
}
