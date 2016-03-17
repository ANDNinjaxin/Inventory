package yt.inventory.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import yt.inventory.App;
import yt.inventory.R;
import yt.inventory.activity.SimpleScannerActivity;
import yt.inventory.logic.Logic;
import yt.inventory.object.Student;

/**
 * Created by Ninjaxin on 11/28/2015.
 */
public class CheckoutBookTwoFragment extends BaseFragment {

    public static final int SCAN_REQUEST_CODE = 0;

    public static final String PASS_STUDENT_ID = "studid";



    public static CheckoutBookTwoFragment newInstance(Student studentID) {
        //params

        CheckoutBookTwoFragment fragment = new CheckoutBookTwoFragment();

        Bundle args = new Bundle();
        args.putSerializable(PASS_STUDENT_ID, studentID);
        fragment.setArguments(args);

        return fragment;
    }

    private View view;
    private RelativeLayout RLbooklevel,
            RLbooknumber,
            RLbookavailable,
            RLbookbarcode;
    private Button btnScan,
            btnAddBook;
    private EditText etbooknumber;
    private Spinner etbooklevel;
    private TextView etbookbarcode,
                    etbookavailability;

    private String sBookID = "";
    private String sBookLevel = "";
    private String sBookNumber = "";
    private boolean sBookAvailable = false;
    private ArrayList<String> sHistory = new ArrayList<>();

    private ArrayAdapter spinnerAdapter;


    private void resetCache() {
        sBookID = "";
        sBookLevel = "";
        sBookNumber = "";
        sBookAvailable = false;
        sHistory.clear();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = super.onCreateView(inflater, container, savedInstanceState);

        RLbooklevel = (RelativeLayout) view.findViewById(R.id.RLbooklevel);
        RLbooknumber = (RelativeLayout) view.findViewById(R.id.RLbooknumber);
        RLbookavailable = (RelativeLayout) view.findViewById(R.id.RLbookavailable);
        RLbookbarcode = (RelativeLayout) view.findViewById(R.id.RLbookbarcode);

        btnScan = (Button) view.findViewById(R.id.btnscan);
        btnAddBook = (Button) view.findViewById(R.id.btnaddbook);

        etbooklevel = (Spinner) view.findViewById(R.id.etbooklevel);
        etbooknumber = (EditText) view.findViewById(R.id.etbooknumber);
        etbookavailability = (TextView) view.findViewById(R.id.etbookavailable);
        etbookbarcode = (TextView) view.findViewById(R.id.etbookbarcode);


        initPreloadUI();
        initListeners(view);
        return view;
    }

    private void initPreloadUI() {
        etbookavailability.setText("N/A");

        spinnerAdapter = ArrayAdapter
                .createFromResource(App.getContext(), R.array.kumon_book_levels,
                        R.layout.spinner_item_book_level);

        spinnerAdapter.setDropDownViewResource(R.layout.spinner_item_book_level);

        etbooklevel.setAdapter(spinnerAdapter);
        etbooklevel.setBackgroundColor(App.resColor(R.color.tint_light_gray));

    }

    private void initListeners(View view) {
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanBarcode();
            }
        });

        btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void getFields() {
        sBookID = etbookbarcode.getText().toString();
        sBookLevel = etbooklevel.getSelectedItem().toString();
        sBookNumber = App.getString(etbooknumber);


        //TODO: check availability
//        if (cbbookavailable.isChecked()) {
//            sBookAvailable = true;
//        } else {
//            sBookAvailable = false;
//        }

    }

    private void clearFields() {
        resetCache();
        //TODO: spinner selection to empty;
        etbooknumber.setText("");
        etbookbarcode.setText("");

        initPreloadUI();
    }

    private boolean nonEmpty(EditText temp) {
        if (temp.getText().toString().isEmpty()) {
            return false;
        }
        return true;
    }

    private void scanBarcode() {
        Intent i = new Intent(App.getContext(), SimpleScannerActivity.class);
        startActivityForResult(i, SCAN_REQUEST_CODE);
//        startActivityForResult(i, 3);
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
            return;
        }

        String contents = data.getStringExtra(SimpleScannerActivity.BARCODE_CONTENTS);

        if ( (contents == null) || (contents.isEmpty()) ) {
            return;
        }

        String tempLevel = Logic.identifyBookLevel(contents);
        String tempBookID = Logic.identifyBookID(contents);

        if (contents.isEmpty() || (contents.trim().length() < 4) ) {
            App.showToast(getString(R.string.error_toast_scan_error));
            return;
        }

        boolean validLevel = false;
        String[] kbooklevels = App.getContext().getResources().getStringArray(R.array.kumon_book_levels);
        for (int i = 0; i < kbooklevels.length; i++) {
            if (kbooklevels[i].equalsIgnoreCase(tempLevel)) {
                validLevel = true;
                break;
            }
        }

        if (validLevel) {
            sBookLevel = tempLevel;
            sBookNumber = tempBookID;
            sBookID = contents;

            int spinnerPosition = spinnerAdapter.getPosition(sBookLevel);
            etbooklevel.setSelection(spinnerPosition);
            etbooknumber.setText(sBookNumber);
            etbookbarcode.setText(contents.trim());

        } else {
            App.showToast("Incorrect Data!");
        }

    }


    @Override
    public Integer getLayoutId() {
        return R.layout.fragment_checkout_book_two;
    }

    @Override
    public String getTitle() {
        return "Checkout";
    }
}
