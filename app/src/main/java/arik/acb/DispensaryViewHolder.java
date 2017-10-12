package arik.acb;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by Craig on 7/26/2017.
 */

public class DispensaryViewHolder extends RecyclerView.ViewHolder {
    TextView dispensaryName, dispensaryAddress, dispensaryWebsite;
    RatingBar dispensaryRating;
    ImageView dispensaryImage;
    LinearLayout button;

    public DispensaryViewHolder(View view){
        super(view);
        dispensaryName = (TextView)view.findViewById(R.id.textViewDispensaryName);
        dispensaryAddress = (TextView)view.findViewById(R.id.textViewDispensaryAddress);
        dispensaryWebsite = (TextView)view.findViewById(R.id.textViewDispensaryWebsite);
        dispensaryRating = (RatingBar)view.findViewById(R.id.ratingBarDispensary);
        dispensaryImage = (ImageView)view.findViewById(R.id.imageViewDispensaryItem);
        button = (LinearLayout)view.findViewById(R.id.linearLayoutListButton);
    }
}
