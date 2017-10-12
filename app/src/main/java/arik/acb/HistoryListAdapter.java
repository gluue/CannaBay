package arik.acb;

import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Jake on 10/12/2017.
 */

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryViewHolder> {
    LayoutInflater inflater;
    List<Dispensary> dispensaryList;
    FragmentManager fragmentManager;



    public HistoryListAdapter(Context c, FragmentManager m){
        inflater = LayoutInflater.from(c);
        fragmentManager = m;
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        final int p = position;
        holder.historyName.setText(SuperVar.history.getHistoryItemName(position));
        holder.historyDesc.setText(SuperVar.history.getHistoryItemDescription(position));
        holder.historyType.setText(SuperVar.history.getHistoryItemType(position));
        holder.historyImage.setBackground(SuperVar.history.getHistoryItemImage(position));

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return SuperVar.history.getHistoryItemNameList().size();
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.history_item_layout, parent, false);

        return new HistoryViewHolder(view);
    }
}

