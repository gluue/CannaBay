package arik.acb;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Jake on 10/12/2017.
 */

public class HistoryViewHolder extends RecyclerView.ViewHolder {

    TextView historyName, historyDesc, historyType;
    ImageView historyImage;
    LinearLayout button;

    public HistoryViewHolder(View view){
        super(view);
        historyName = (TextView)view.findViewById(R.id.textViewHistoryName);
        historyDesc = (TextView)view.findViewById(R.id.textViewHistoryDesc);
        historyType = (TextView)view.findViewById(R.id.textViewHistoryType);
        historyImage = (ImageView)view.findViewById(R.id.imageViewHistoryItem);
        button = (LinearLayout)view.findViewById(R.id.linearLayoutListButton);
    }
}
