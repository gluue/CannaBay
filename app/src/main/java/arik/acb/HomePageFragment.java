package arik.acb;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Craig on 8/28/2017.
 */

public class HomePageFragment extends Fragment {
   RecyclerView historyList;
    Context context;
    TextView noSearchHistoryText;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_page_layout, container, false);

        historyList = (RecyclerView)rootView.findViewById(R.id.rvHistoryList);
        historyList.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        noSearchHistoryText = (TextView)rootView.findViewById(R.id.textViewHomeNoHistory);

        historyList.setAdapter(new HistoryListAdapter(rootView.getContext(), getFragmentManager()));

        if(SuperVar.history.getHistoryItemNameList().size()>0){
            noSearchHistoryText.setVisibility(View.INVISIBLE);
        }else{
            noSearchHistoryText.setVisibility(View.VISIBLE);
        }


        return rootView;
    }
}
