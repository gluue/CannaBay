package arik.acb;

import android.media.Rating;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by Jake on 7/26/2017.
 */

public class ProductViewHolder extends RecyclerView.ViewHolder {
    TextView productName, productDispensaryName, productDispensaryAddress;
    RatingBar productRating;
    ImageView productImage;
    LinearLayout button;

    public ProductViewHolder(View view){
        super(view);

        productName = (TextView)view.findViewById(R.id.textViewProductItem);
        productRating = (RatingBar)view.findViewById(R.id.ratingBarProductItem);
        productImage = (ImageView)view.findViewById(R.id.imageViewProductItem);
        productDispensaryAddress = (TextView)view.findViewById(R.id.textViewProductItemAddress);
        productDispensaryName = (TextView)view.findViewById(R.id.textViewProductItemDispensary);
        button = (LinearLayout)view.findViewById(R.id.linearLayoutListButton);

    }

}
