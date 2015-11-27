package yt.inventory.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import yt.inventory.App;
import yt.inventory.R;
import yt.inventory.object.Student;

/**
 * Created by Ninjaxin on 11/3/15.
 */
public class ManualInputStudentFragment extends BaseFragment {

    public static ManualInputStudentFragment newInstance() {
        //params

        ManualInputStudentFragment fragment = new ManualInputStudentFragment();

//        Bundle args = new Bundle();
//        args.putSerializable();
//        fragment.setArguments(args);

        return fragment;
    }

    private View view;
    private Button buttonAddStudent;
    private EditText etStudentID,
                    etFirstName,
                    etLastName,
                    etDOB;

    private String sID = "";
    private String sFirstName = "";
    private String sLastName = "";
    private String sDOB = "";

    private Spinner spinner;
    private ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);






    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = super.onCreateView(inflater, container, savedInstanceState);

        buttonAddStudent = (Button) view.findViewById(R.id.btnaddstudent);
        etStudentID = (EditText) view.findViewById(R.id.etstudentid);
        etFirstName = (EditText) view.findViewById(R.id.etstudentfirstname);
        etLastName = (EditText) view.findViewById(R.id.etstudentlastname);
        etDOB = (EditText) view.findViewById(R.id.etstudentdob);

        spinner = (Spinner) view.findViewById(R.id.spinnerstudents);
        List<String> studs = new ArrayList<String>();
        if (studs != null) {
            studs.clear();
        }
        for (Student student: App.getStudentList()) {
            studs.add(student.getLastName() + ", " + student.getFirstName());
        }

        adapter = new ArrayAdapter<String>
                (App.getContext(), R.layout.spinner_item_book_level, studs);
        adapter.setDropDownViewResource(R.layout.spinner_item_book_level);
        spinner.setAdapter(adapter);

        setupListeners(view);

        return view;
    }

    private void setupListeners(View view) {

        buttonAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStudent();
            }
        });

    }

    private void addStudent() {
        boolean validData = (nonEmpty(etFirstName) && nonEmpty(etLastName) && validDOB(etDOB));

        if (!validData) {
            App.showToast(R.string.error_toast_required_fields);
            return;
        }

        boolean yesID = validStudentID(etStudentID);

        Student student;

        if (yesID) {
            student = new Student(sID, sFirstName, sLastName, sDOB);
        } else {
            student = new Student(sFirstName, sLastName, sDOB);
        }

        App.addStudent(student);
        App.showToast("Successfully added student!");
    }

    private boolean nonEmpty(EditText temp) {
        if (temp.getText().toString().isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean validStudentID(EditText temp) {
        int tempID = -1;
        try {
            tempID = Integer.parseInt(temp.getText().toString());
            if (tempID > 1) {
                return true;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return false;
    }

    private boolean validDOB(EditText temp) {
        int tempDOB = -1;
        try {
            tempDOB = Integer.parseInt(temp.getText().toString());
            String cacheDOB = temp.getText().toString();
            String dobMonth = cacheDOB.substring(0, 2);
            String dobDay = cacheDOB.substring(2, 4);
            String dobYear = cacheDOB.substring(4, 7);
            cacheDOB = "";
            cacheDOB = dobYear + dobMonth + dobDay;
            sDOB = cacheDOB;

            if ( (tempDOB > 10000000) || (tempDOB < 99999999) ) {
                return true;
            } else {
                App.showToast("Invalid Birthdate!");
                return false;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (StringIndexOutOfBoundsException e) {
            e.printStackTrace();
            App.showToast("Invalid Birthdate!");
        }

        return false;
    }

    private void resetCacheVariables() {
        sID = "";
        sFirstName = "";
        sLastName = "";
        sDOB = "";
    }

    @Override
    public Integer getLayoutId() {
        return R.layout.fragment_manual_input_student;
    }

    @Override
    public String getTitle() {
        return null;
    }
}
