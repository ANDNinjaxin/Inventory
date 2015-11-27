package yt.inventory.utility;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.w3c.dom.EntityReference;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;

import yt.inventory.App;
import yt.inventory.logic.Logic;
import yt.inventory.object.Student;

/**
 * Created by Ninjaxin on 11/26/2015.
 */
public class ReadExcelFile {

    public final static String TAG = "ReadExcelFile.class";


    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    public static void readFile(Context context, String filename) {
        ArrayList<Student> studentArrayList = new ArrayList<>();

        try {
            String localfilepath = "/" + filename;
            String basepath = String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));
            String filepath = basepath + localfilepath;

            File file = new File( filepath );

            FileInputStream fileInputStream = new FileInputStream(file);

            POIFSFileSystem poifsFileSystem = new POIFSFileSystem(fileInputStream);
            HSSFWorkbook workbook = new HSSFWorkbook(poifsFileSystem);
            HSSFSheet sheet = workbook.getSheetAt(0);

            Iterator rowIterator = sheet.rowIterator();
            if (rowIterator.hasNext()) {
                rowIterator.next();
            }
            rowloop:
            while (rowIterator.hasNext()) {
                HSSFRow row = (HSSFRow) rowIterator.next();
                Iterator cellIterator = row.cellIterator();

                final int sID = 0,
                        sOID = 1,
                        sLN = 2,
                        sFN = 3,
                        sDOB = 4,
                        sEnd = 5;
                int counter = 0;
                String cID = "";
                String cLN = "";
                String cFN = "";
                String cDOB = "";

                columnloop:
                while (cellIterator.hasNext()) {
                    HSSFCell cell = (HSSFCell) cellIterator.next();
                    String contents = cell.toString().trim();
                    if (contents.isEmpty() && (counter != sID) && (counter > 4)) {
                        break rowloop;
                    }

                    switch (counter) {
                        case sID:
                            cID = contents; break;
                        case sOID:
                            //empty space for old IDs
                            break;
                        case sLN:
                            cLN = contents; break;
                        case sFN:
                            cFN = contents; break;
                        case sDOB:
                            cDOB = contents; break;
                        case sEnd:
                            cID = Logic.inputStudentID(cID);
                            cDOB = Logic.inputStudentDOB(cDOB);
                            Student stu = new Student(cID, cFN, cLN, cDOB);
                            studentArrayList.add(stu);

                        default:
                            Log.v(TAG, "Out-of-bounds cell or invalid value: ");
                            break columnloop;
                    }

                    counter++;
                }

            }

            App.setStudentList(studentArrayList);
            App.showToast("Successfully read xlsx file");

        } catch (Exception e) {
            e.printStackTrace();
            App.showToast("Error on ReadExcelFile");
        }

    }


    public static boolean saveFile(Context context, String mytext){
        Log.i("TESTE", "SAVE");
        try {
            Log.v(TAG, Environment.getDataDirectory().getPath());
            Log.v(TAG, Environment.DIRECTORY_DOWNLOADS);
            Log.v(TAG, String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)));
            Log.v(TAG, Environment.getRootDirectory().getAbsolutePath());

            FileOutputStream fos = context.openFileOutput("file_name" + ".txt", Context.MODE_PRIVATE);
            Writer out = new OutputStreamWriter(fos);
            out.write(mytext);
            out.close();
            Log.v(TAG, "SAVE WORKS");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            Log.v(TAG, "SAVE DOESNT WORK");

            return false;
        }
    }
}
