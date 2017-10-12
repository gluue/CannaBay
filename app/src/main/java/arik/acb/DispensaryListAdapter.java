package arik.acb;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Craig on 7/26/2017.
 */

public class DispensaryListAdapter extends RecyclerView.Adapter<DispensaryViewHolder> {
    LayoutInflater inflater;
    List<Dispensary> dispensaryList;
    FragmentManager fragmentManager;



    public DispensaryListAdapter(Context c, List<Dispensary> list, FragmentManager m){
        inflater = LayoutInflater.from(c);
        dispensaryList = list;
        fragmentManager = m;
    }

    @Override
    public void onBindViewHolder(DispensaryViewHolder holder, int position) {
        final int p = position;
        holder.dispensaryName.setText(dispensaryList.get(position).getDispensaryName());
        holder.dispensaryAddress.setText(dispensaryList.get(position).getDispensaryAddress());
        holder.dispensaryWebsite.setText(dispensaryList.get(position).getDispensaryWebsite());
        holder.dispensaryRating.setRating(dispensaryList.get(position).getDispensaryRating());
        holder.dispensaryImage.setBackground(dispensaryList.get(position).getIconImage());


                holder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (SuperVar.targetProductFragment != null && SuperVar.targetDispensary != null) {
                            if (!SuperVar.targetDispensary.getDispensaryName().equals(dispensaryList.get(p).getDispensaryName())) {
                                SuperVar.targetProductFragment = null;
                            }
                        }

                        SuperVar.productListType = "f";
                        SuperVar.targetDispensary = dispensaryList.get(p);
                        DispensaryDetailFragment fragment = new DispensaryDetailFragment();
                        SuperVar.targetDispensaryFragment = fragment;
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_left);
                        transaction.replace(R.id.frameLayoutMain, SuperVar.targetDispensaryFragment).commit();
                    }
                });

    }

    @Override
    public int getItemCount() {
        return dispensaryList.size();
    }

    @Override
    public DispensaryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.dispensary_item_layout, parent, false);

        return new DispensaryViewHolder(view);
    }
}
