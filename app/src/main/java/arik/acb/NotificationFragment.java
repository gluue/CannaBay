package arik.acb;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Jake on 7/26/2017.
 */

public class NotificationFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.notification_fragment_layout, container, false);
        SuperVar.lastPageID = SuperVar.pageID;
        SuperVar.pageID = "notification";
        SuperVar.mainNavigationView.getMenu().getItem(2).setChecked(true);
        return rootView;
    }
}
