package yt.inventory.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import yt.inventory.App;
import yt.inventory.R;
import yt.inventory.activity.SimpleScannerActivity;
import yt.inventory.logic.Logic;
import yt.inventory.object.Student;

/**
 * Created by Ninjaxin on 11/27/2015.
 */
public class CheckoutBookFragment extends BaseFragment {

    public static final int SCAN_REQUEST_CODE = 0;

    private final int USE_SCANNED = 0,
                    USE_SPINNER = 1,
                    USE_MANUAL = 2;
    private boolean scanned = false;


    private View view;
    RelativeLayout RLscancontainer,
                    RLothercontainer,
                    RLscansuccess;
    private Button btnscan,
                    btnclear,
                    btnnext;
    private EditText etfirstname,
                    etlastname;
    private TextView tvfirstname,
                    tvlastname;
    private Spinner spinner;

    private ArrayAdapter<Student> adapter;


    private String cachedID = "";
    private final Student emptyStudent = new Student("-10", "New Student", "", "");
    private Student cachedStudent = null;




    public static CheckoutBookFragment newInstance() {
        //params

        CheckoutBookFragment fragment = new CheckoutBookFragment();

//        Bundle args = new Bundle();
//        args.putSerializable();
//        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = super.onCreateView(inflater, container, savedInstanceState);

        RLscancontainer = (RelativeLayout) view.findViewById(R.id.RLscanstudentcontainer);
        RLothercontainer = (RelativeLayout) view.findViewById(R.id.RLotherstudentoptioncontainer);
        RLscansuccess = (RelativeLayout) view.findViewById(R.id.RLscansuccess);
        btnscan = (Button) view.findViewById(R.id.btnscanstudent);
        btnclear = (Button) view.findViewById(R.id.btnclearcheckoutstudent);
        btnnext = (Button) view.findViewById(R.id.btnnextcheckoutstudent);
        etfirstname = (EditText) view.findViewById(R.id.etnewfirstname);
        etlastname = (EditText) view.findViewById(R.id.etnewlastname);
        spinner = (Spinner) view.findViewById(R.id.spstudentlist);
        tvfirstname = (TextView) view.findViewById(R.id.tvsuccessfirstname);
        tvlastname = (TextView) view.findViewById(R.id.tvsuccesslastname);

        List<String> studs = new ArrayList<String>();
        if (studs != null) {
            studs.clear();
        }
        for (Student student: App.getStudentList()) {
            studs.add(student.getLastName() + ", " + student.getFirstName());
        }
        adapter = new ArrayAdapter<Student>
                (App.getContext(), R.layout.spinner_item_book_level, App.getStudentList());
        adapter.setDropDownViewResource(R.layout.spinner_item_book_level);
        spinner.setAdapter(adapter);

        hideretrieved();
        setupListeners();

        return view;
    }

    private void setupListeners() {
        btnscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanBarcode();
            }
        });
        btnclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFields();
            }
        });
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextFragment();
            }
        });

    }

    private void hideretrieved() {
        RLscansuccess.setVisibility(View.GONE);
        RLothercontainer.setVisibility(View.VISIBLE);
    }

    private void scansuccess() {
        RLscansuccess.setVisibility(View.VISIBLE);
        RLothercontainer.setVisibility(View.GONE);
        scanned = true;
    }

    private void resetFields() {
//        spinner.setSelected();
        scanned = false;
        cachedID = "";
        cachedStudent = null;
        etfirstname.setText("");
        etlastname.setText("");
        tvfirstname.setText("");
        tvlastname.setText("");
        hideretrieved();
    }

    private void nextFragment() {
        if (scanned) {
            App.getMainActivity().gotoCheckoutBookTwo(cachedStudent);

        } else if (!spinner.getSelectedItem().toString().equalsIgnoreCase(getString(R.string.empty_student))) {
            //Get item position (maybe +1 for offset) of "NEW STUDENT"
            int position = spinner.getSelectedItemPosition();
            App.getMainActivity().gotoCheckoutBookTwo(App.getStudent(position));

        } else if (!App.isEmpty(etfirstname) && (!App.isEmpty(etlastname)) ) {
            Student student;
            student = new Student("", App.getString(etfirstname), App.getString(etlastname), "");
            App.addStudent(student);

        }


        String tfn = tvfirstname.getText().toString().trim();
        String tln = tvlastname.getText().toString().trim();
        if (tfn.isEmpty() && tln.isEmpty()) {
            return;
        }

            //Deprecated
//        App.getMainActivity().gotoCheckoutBookTwo(cachedID);
    }

    private void scanBarcode() {
        Intent i = new Intent(App.getContext(), SimpleScannerActivity.class);
        startActivityForResult(i, SCAN_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SCAN_REQUEST_CODE) {
            onScanResult(resultCode, data);
            return;
        }
    }

    private void onScanResult(int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            App.showToast("Bad scan");
            return;
        }

        String contents = data.getStringExtra(SimpleScannerActivity.BARCODE_CONTENTS);

        if ( (contents == null) || (contents.isEmpty()) ) {
            return;
        }

        String scanned = contents.trim();

        try {
            long checkint = Long.parseLong(scanned);
            if (scanned.length() != App.STANDARD_STUDENT_ID_LENGTH) {
                App.showToast(getString(R.string.error_toast_scan_student));
                return;
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
            return;
        }

        for (Student student: App.getStudentList()) {
            if (student.getId().equalsIgnoreCase(scanned)) {
                scansuccess();
                cachedID = student.getId();
                cachedStudent = student;
                tvfirstname.setText(student.getFirstName());
                tvlastname.setText(student.getLastName());
            }
        }
    }

    @Override
    public Integer getLayoutId() {
        return R.layout.fragment_checkout_book;
    }

    @Override
    public String getTitle() {
        return "Checkout";
    }
}
