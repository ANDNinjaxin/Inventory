package yt.inventory.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import yt.inventory.App;
import yt.inventory.R;

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


        initPreloadUI(view);
        initListeners(view);
        return view;
    }

    private void initPreloadUI(View view) {
        RLbooktitle.setVisibility(View.GONE);
        RLbookauthor.setVisibility(View.GONE);
        RLbookavailable.setVisibility(View.GONE);
        RLbookbarcode.setVisibility(View.GONE);
        btnAddBook.setVisibility(View.GONE);
        cbbookavailable.setChecked(true);



    }

    private void initListeners(View view) {
        btnGetInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etbooklevel.getSelectedItem().toString().trim().isEmpty()
                        && nonEmpty(etbooknumber)) {

                } else {
                    App.showToast(R.string.error_toast_required_fields);
                }
            }
        });

        btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean validBook = (etbooklevel.)
                            && nonEmpty(etbooknumber)
                            && nonEmpty(etbooktitle)
                            && nonEmpty(etbookauthor));
                if (validBook) {
                    //need valid book number (int)
                }
            }
        });

    }

    private void getBookInfo() {

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
