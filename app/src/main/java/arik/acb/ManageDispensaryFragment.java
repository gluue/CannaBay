package arik.acb;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Craig on 8/17/2017.
 */

public class ManageDispensaryFragment extends Fragment {
    EditText productName, productDescription, productTHC, productCBC, productCBD;
    Button addProduct;
    Spinner productTypeSpinner;
    String productTypeString;
    Product product;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.manage_dispensary_layout, container, false);

        product = new Product();

        productName = (EditText)rootView.findViewById(R.id.editTextManageDispensaryProductName);
        productDescription = (EditText)rootView.findViewById(R.id.editTextManageDispensaryProductDetail);
        productTHC = (EditText)rootView.findViewById(R.id.editTextManageDispensaryProductTHC);
        productCBC = (EditText)rootView.findViewById(R.id.editTextManageDispensaryProductCBC);
        productCBD = (EditText)rootView.findViewById(R.id.editTextManageDispensaryProductCBD);

        addProduct = (Button) rootView.findViewById(R.id.buttonManageDispensaryAddProduct);
        productTypeSpinner = (Spinner)rootView.findViewById(R.id.spinnerManageDispensaryProductType);

        List<String> productTypeList = new ArrayList<>();
        productTypeList.add("flower");
        productTypeList.add("concentrate");
        productTypeList.add("edible");

        productTypeSpinner.setAdapter(new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_spinner_item, productTypeList));
        productTypeSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        productTypeString = "f";
                        product.setImage(getResources().getDrawable(R.drawable.flower));
                        product.setIconImage(getResources().getDrawable(R.drawable.flower_image_icon));
                        break;
                    case 1:
                        productTypeString = "c";
                        product.setImage(getResources().getDrawable(R.drawable.concentrate));
                        product.setIconImage(getResources().getDrawable(R.drawable.concentrate_image_icon));
                        break;
                    case 2:
                        productTypeString = "e";
                        product.setImage(getResources().getDrawable(R.drawable.edible));
                        product.setIconImage(getResources().getDrawable(R.drawable.edible_image_icon));
                        break;
                }
            }
        });

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                product.setProductName(productName.getText().toString());
                product.setProductType(productTypeString);
                product.setProductDescription(productDescription.getText().toString());
                product.setDispensary(SuperVar.targetDispensary);

                for(Dispensary d : SuperVar.dispensaryList){
                    if(d.getDispensaryName().equals(SuperVar.targetDispensary)){
                        d.addToProductList(product);
                    }
                }
            }
        });

        return rootView;
    }
}
