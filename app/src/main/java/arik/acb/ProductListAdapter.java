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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jake on 7/26/2017.
 */

public class ProductListAdapter extends RecyclerView.Adapter<ProductViewHolder> {

    LayoutInflater inflater;
    List<Product> productList, totalList;
    FragmentManager manager;
    Context context;
    OnSwipeTouchListener swipeTouchListener;

    public ProductListAdapter(Context c, List<Product> list, FragmentManager m){
        inflater = LayoutInflater.from(c);
        totalList = list;
        manager = m;
        productList = new ArrayList<>();
        context = c;

        for(Product p : totalList){
            if(p.getProductType().equals(SuperVar.productListType)){
                productList.add(p);
            }
        }
    }


    @Override
    public void onBindViewHolder(final ProductViewHolder holder, int position) {
        final int p = position;
        holder.productName.setText(productList.get(position).getProductName());
        holder.productRating.setRating(productList.get(position).getRating());
        holder.productDispensaryName.setText(productList.get(position).getDispensaryName());
        holder.productDispensaryAddress.setText(productList.get(position).getDispensaryAddress());
        switch (SuperVar.productListType){
            case "f":
                holder.productImage.setBackground(productList.get(position).getImage());
                break;
            case "c":
                holder.productImage.setBackground(productList.get(position).getImage());
                break;
            case "e":
                holder.productImage.setBackground(productList.get(position).getImage());
                break;
        }

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SuperVar.targetProduct = productList.get(p);
                ProductDetailFragment detailFragment = new ProductDetailFragment();
                SuperVar.targetProductFragment = detailFragment;
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_left);
                transaction.replace(R.id.frameLayoutMain, SuperVar.targetProductFragment).commit();
            }
        });

        swipeTouchListener = new OnSwipeTouchListener(holder.button.getContext()) {
            @Override
            public void onSwipeLeft() {
                //FORWARD SWIPE
                Toast.makeText(context, "YOU'RE SWIPING LEFT ON A PRODUCT", Toast.LENGTH_LONG).show();


            }
            @Override
            public void onSwipeRight () {
                //BACK SWIPE
                Toast.makeText(context, "YOU'RE SWIPING RIGHT ON A PRODUCT", Toast.LENGTH_LONG).show();

            }
        };



    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.product_item_layout, parent, false);

        return new ProductViewHolder(view);
    }


}
