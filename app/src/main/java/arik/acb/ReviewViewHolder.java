package arik.acb;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by Jake on 8/7/2017.
 */

public class ReviewViewHolder extends RecyclerView.ViewHolder {
    TextView reviewUserName, reviewDescription;
    RatingBar reviewRating;
    ImageView reviewImage;

    public ReviewViewHolder(View view){
        super(view);

        reviewUserName = (TextView)view.findViewById(R.id.textViewReviewUserName);
        reviewDescription = (TextView)view.findViewById(R.id.textViewReviewDescription);

        reviewRating = (RatingBar)view.findViewById(R.id.ratingBarReview);
        reviewImage = (ImageView)view.findViewById(R.id.imageViewReviewAvatar);

    }
}
