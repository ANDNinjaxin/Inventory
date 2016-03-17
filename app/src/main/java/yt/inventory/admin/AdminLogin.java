package yt.inventory.admin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import yt.inventory.App;
import yt.inventory.R;
import yt.inventory.fragment.BaseFragment;

/**
 * Created by Ninjaxin on 11/30/2015.
 */
public class AdminLogin extends BaseFragment {

    private View view;
    private EditText loginField;
    private Button buttonLogin;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = super.onCreateView(inflater, container, savedInstanceState);

        loginField = (EditText) view.findViewById(R.id.etadminlogin);
        buttonLogin = (Button) view.findViewById(R.id.btnadminloginbutton);


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validatePin(App.getString(loginField));
            }
        });

        return view;
    }

    private boolean validatePin(String rawpin) {
        try {
            int pin = Integer.parseInt(rawpin);
            App.validateLogin(pin);
            return true;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Integer getLayoutId() {
        return R.layout.fragment_admin_login;
    }

    @Override
    public String getTitle() {
        return "Admin Login";
    }
}
