package arik.acb;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.SupportErrorDialogFragment;

/**
 * Created by Jake on 8/13/2017.
 */

public class LogInFragment extends Fragment{

    Button logInButton;
    boolean loggedIn;
    EditText userField, passField;
    Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.log_in_layout, container, false);



        logInButton = (Button)rootView.findViewById(R.id.buttonLogIn);
        userField = (EditText)rootView.findViewById(R.id.editTextUserName);
        passField = (EditText)rootView.findViewById(R.id.editTextPassword);

        context = rootView.getContext();
        //GET USER PASS
        //FOR TEST We'LL USE a FIXED USER LIST

        loggedIn = false;

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(User u : SuperVar.userList){
                    if(userField.getText().toString().equals(u.getUserName())){
                        if(passField.getText().toString().equals(u.getUserPassword())){
                            u.setUserAvater(getResources().getDrawable(R.drawable.avatar_default));
                            SuperVar.currentUser = u;
                            loggedIn = true;
                            getFragmentManager().beginTransaction().replace(R.id.frameLayoutMain, new SplashScreenFragment()).commit();
                        }
                    }
                }
                if(!loggedIn){
                    Toast.makeText(context, "Invalid LogIn, Try again.", Toast.LENGTH_LONG).show();
                }
            }
        });


        return rootView;
    }
}
