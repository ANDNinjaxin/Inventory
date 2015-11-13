package yt.inventory.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import yt.inventory.App;
import yt.inventory.R;
import yt.inventory.object.Book;

/**
 * Created by Ninjaxin on 11/3/15.
 */
public class ManualInputBookFragment extends BaseFragment {

    public static ManualInputBookFragment newInstance() {
        //params

        ManualInputBookFragment fragment = new ManualInputBookFragment();

//        Bundle args = new Bundle();
//        args.putSerializable();
//        fragment.setArguments(args);

        return fragment;
    }


    private View view;
    private RelativeLayout RLbooklevel,
                            RLbooknumber,
                            RLbooksearch,
                            RLbooktitle,
                            RLbookauthor,
                            RLbookavailable,
                            RLbookbarcode;
    private Button btnGetInfo,
                    btnAddBook;
    private EditText etbooknumber,
                    etbooktitle,
                    etbookauthor;
    private Spinner etbooklevel;
    private TextView etbookbarcode;
    private CheckBox cbbookavailable;

    private int sBookID = -1;
    private String sBookLevel = "";
    private int sBookNumber = -1;
    private String sBookTitle = "";
    private String sBookAuthor = "";
    private boolean sBookAvailable = false;
    private ArrayList<String> sHistory = new ArrayList<>();


    private void resetCache() {
        sBookID = -1;
        sBookLevel = "";
        sBookNumber = -1;
        sBookTitle = "";
        sBookAuthor = "";
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
        RLbooksearch = (RelativeLayout) view.findViewById(R.id.RLBookSearch);
        RLbooktitle = (RelativeLayout) view.findViewById(R.id.RLbooktitle);
        RLbookauthor = (RelativeLayout) view.findViewById(R.id.RLbookauthor);
        RLbookavailable = (RelativeLayout) view.findViewById(R.id.RLbookavailable);
        RLbookbarcode = (RelativeLayout) view.findViewById(R.id.RLbookbarcode);

        btnGetInfo = (Button) view.findViewById(R.id.btngetinfo);
        btnAddBook = (Button) view.findViewById(R.id.btnaddbook);

        etbooklevel = (Spinner) view.findViewById(R.id.etbooklevel);
        etbooknumber = (EditText) view.findViewById(R.id.etbooknumber);
        etbooktitle = (EditText) view.findViewById(R.id.etbooktitle);
        etbookauthor = (EditText) view.findViewById(R.id.etbookauthor);
        cbbookavailable = (CheckBox) view.findViewById(R.id.etbookavailable);
        etbookbarcode = (TextView) view.findViewById(R.id.etbookbarcode);


        initPreloadUI();
        initListeners(view);
        return view;
    }

    private void initPreloadUI() {
        RLbooksearch.setVisibility(View.VISIBLE);
        RLbooktitle.setVisibility(View.GONE);
        RLbookauthor.setVisibility(View.GONE);
        RLbookavailable.setVisibility(View.GONE);
        cbbookavailable.setChecked(true);
        RLbookbarcode.setVisibility(View.GONE);
        btnAddBook.setVisibility(View.GONE);

        ArrayAdapter spinnerAdapter = ArrayAdapter
                .createFromResource(App.getContext(), R.array.kumon_book_levels,
                        android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        etbooklevel.setAdapter(spinnerAdapter);

    }

    private void initListeners(View view) {
        btnGetInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!App.isEmpty(etbooklevel.getSelectedItem())
                        && App.nonZero(etbooknumber)) {
                    resetCache();
                    sBookLevel = App.getString(etbooklevel.getSelectedItem());
                    sBookNumber = App.getInt(etbooknumber);
                    getBookInfo();

                } else {
                    App.showToast(R.string.error_toast_required_fields);
                }
            }
        });

        btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void getBookInfo() {
        //getBookDBRequest();

        RLbooksearch.setVisibility(View.GONE);
        RLbooktitle.setVisibility(View.VISIBLE);
        RLbookauthor.setVisibility(View.VISIBLE);
        RLbookavailable.setVisibility(View.VISIBLE);
        RLbookbarcode.setVisibility(View.VISIBLE);

    }

    //Pull from public ISBN DB
    private void getBookDBRequest() {

    }

    private void addBook() {
        //TODO: Set id = barcode;
        //TODO: Create unique barcode generator;

        boolean validBook = (!App.isEmpty(etbooklevel.getSelectedItem()))
                && nonEmpty(etbooknumber)
                && nonEmpty(etbooktitle)
                && nonEmpty(etbookauthor);
        if (validBook) {
            //TODO: need valid book number (int)

            Book book;
            book = new Book(sBookID, sBookLevel, sBookNumber, sBookTitle, sBookAuthor, sBookAvailable);
            App.addBook(book);

        }
    }

    private void getFields() {
        sBookID = App.getInt(etbookbarcode);
        sBookLevel = etbooklevel.getSelectedItem().toString();
        sBookNumber = App.getInt(etbooknumber);
        sBookTitle = etbooktitle.getText().toString();
        sBookAuthor = etbookauthor.getText().toString();

        if (cbbookavailable.isChecked()) {
            sBookAvailable = true;
        } else {
            sBookAvailable = false;
        }

    }

    private void clearFields() {
        resetCache();
        //TODO: spinner selection to empty;
        etbooknumber.setText("");
        etbooktitle.setText("");
        etbookauthor.setText("");
        cbbookavailable.setChecked(true);
        etbookbarcode.setText("");

        initPreloadUI();
    }

    private boolean nonEmpty(EditText temp) {
        if (temp.getText().toString().isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public Integer getLayoutId() {
        return R.layout.fragment_manual_input_book;
    }

    @Override
    public String getTitle() {
        return null;
    }
}
