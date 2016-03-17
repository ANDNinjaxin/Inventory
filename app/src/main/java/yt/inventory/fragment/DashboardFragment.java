package yt.inventory.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import yt.inventory.App;
import yt.inventory.R;

/**
 * Created by Ninjaxin on 11/28/2015.
 */
public class DashboardFragment extends BaseFragment {

    private View view;
    private ListView lvUserDashboard,
            lvAdminDashboard;

    private final String
            CHECK_OUT = "Checkout Book",
            CHECK_IN = "Return Book",
            NEW_STUDENT = "New Student",
            NEW_BOOK = "New Book",
            SWITCH_ADMIN = "Admin Mode",
            SWITCH_USER = "EXIT Admin Mode",
            RESET_PIN = "Reset Pin",
            EDIT_STUDENT = "Edit Student",
            EDIT_BOOK = "Edit Book",
            EDIT_TRANSACTION = "Edit Transaction";


    private String[] adminlist = {
            CHECK_OUT,
            CHECK_IN,
            NEW_STUDENT,
            NEW_BOOK,
            SWITCH_USER,
            EDIT_STUDENT,
            EDIT_BOOK,
            EDIT_TRANSACTION,
            RESET_PIN
    };
    private String[] userlist = {
            CHECK_OUT,
            CHECK_IN,
            NEW_STUDENT,
            NEW_BOOK,
            RESET_PIN,
            SWITCH_ADMIN
    };
    private ArrayAdapter<String> adapteradmin, adapteruser;




    public static DashboardFragment newInstance() {
        //params

        DashboardFragment fragment = new DashboardFragment();

//        Bundle args = new Bundle();
//        args.putSerializable();
//        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapteradmin = new ArrayAdapter<String>(App.getContext(), R.layout.spinner_item_book_level, adminlist);
        adapteruser = new ArrayAdapter<String>(App.getContext(), R.layout.spinner_item_book_level, userlist);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = super.onCreateView(inflater, container, savedInstanceState);

        lvUserDashboard = (ListView) view.findViewById(R.id.lvdashboarduser);
        lvAdminDashboard = (ListView) view.findViewById(R.id.lvdashboardadmin);
        setDashboard();
        lvAdminDashboard.setAdapter(adapteradmin);
        lvUserDashboard.setAdapter(adapteruser);

        setupDashboardListeners();

        return view;
    }


    private void setupDashboardListeners() {
        lvUserDashboard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });


    }

    private void dashboardChoice(String choice) {

    }

    private void setDashboard() {
        if (App.isAdmin()) {
            lvAdminDashboard.setVisibility(View.VISIBLE);
            lvUserDashboard.setVisibility(View.GONE);
        } else {
            lvUserDashboard.setVisibility(View.VISIBLE);
            lvAdminDashboard.setVisibility(View.GONE);
        }
    }





    @Override
    public Integer getLayoutId() {
        return R.layout.fragment_dashboard;
    }

    @Override
    public String getTitle() {
        return null;
    }
}
