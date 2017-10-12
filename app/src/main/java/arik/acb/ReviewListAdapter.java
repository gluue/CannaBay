package arik.acb;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jake on 7/26/2017.
 */

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewViewHolder> {

    LayoutInflater inflater;
    List<Review> reviewList, totalList;
    FragmentManager manager;
    Context context;

    public ReviewListAdapter(Context c, List<Review> list, FragmentManager m){
        inflater = LayoutInflater.from(c);
        totalList = list;
        manager = m;
        reviewList = list;
        context = c;

    }


    @Override
    public void onBindViewHolder(final ReviewViewHolder holder, int position) {

        holder.reviewUserName.setText(reviewList.get(position).getUser().getUserName());
        holder.reviewDescription.setText(reviewList.get(position).getDescription());
        holder.reviewRating.setRating((float)reviewList.get(position).getRating());

    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.review_item_layout, parent, false);

        return new ReviewViewHolder(view);
    }


}
