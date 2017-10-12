package arik.acb;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Jake on 7/26/2017.
 */

public class ProductDetailFragment extends Fragment {
    ImageView productImage;
    AppBarLayout appBar;
    AppBarLayout.OnOffsetChangedListener mListener;
    Toolbar toolbar;
    FloatingActionButton fab;
    RecyclerView reviewList;
    List<Review> productReviewList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.product_detail_layout, container, false);
        productImage = (ImageView)rootView.findViewById(R.id.imageViewProductDetail);
        appBar = (AppBarLayout)rootView.findViewById(R.id.appBarProduct);
        toolbar = (Toolbar)rootView.findViewById(R.id.toolbar);
        fab = (FloatingActionButton)rootView.findViewById(R.id.fabProduct);
        reviewList = (RecyclerView)rootView.findViewById(R.id.recyclerViewProductReview);
        productReviewList = new ArrayList<>();

        reviewList.setNestedScrollingEnabled(false);

        getFragmentManager().beginTransaction().replace(R.id.frameLayoutProductStats, new ProductStatFragment()).commit();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SuperVar.targetDispensary = SuperVar.targetProduct.getDispensary();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_left);
                transaction.replace(R.id.frameLayoutMain, new DispensaryDetailFragment()).commit();
            }
        });


        productImage.setBackground(SuperVar.targetProduct.getImage());

        SuperVar.lastPageID = SuperVar.pageID;
        SuperVar.pageID = "productDetail";

        mListener = new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(verticalOffset == toolbar.getHeight() - appBarLayout.getHeight()){
                    toolbar.setTitle(SuperVar.targetProduct.getProductName());
                    fab.hide();
                }else{
                    toolbar.setTitle("");
                    fab.show();
                }
            }
        };

        appBar.addOnOffsetChangedListener(mListener);

        Random random = new Random();

        for(int i = 0; i < 5; i++){
            User user = new User();
            user.setUserName("user " + Integer.toString(i));
            Review review = new Review();
            review.setUser(user);
            review.setDescription("SOME FUCKING DESCRIPTION OF SOME SHIT, I DONT KNOW IT PROBABLY WILL GET YOU HIGH THOUGH....");
            review.setRating(random.nextInt(5)+1);
            productReviewList.add(review);
            System.out.println("ADDED REVIEW " + Integer.toString(i) + " TO LIST");
        }

        reviewList.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        reviewList.setAdapter(new ReviewListAdapter(rootView.getContext(), productReviewList, getFragmentManager()));


        SuperVar.history.addToNameList(SuperVar.targetProduct.getProductName());
        SuperVar.history.addToDescriptionList("ITS A WONDERFUL PRODUCT I GUESS");
        SuperVar.history.addToTypeList("product");
        SuperVar.history.addToHistoryItemImageList(SuperVar.targetProduct.getImage());


        return rootView;
    }
}
