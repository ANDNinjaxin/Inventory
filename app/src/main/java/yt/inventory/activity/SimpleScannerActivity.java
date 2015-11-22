package yt.inventory.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by Ninjaxin on 11/20/2015.
 */

public class SimpleScannerActivity extends ActionBarActivity implements ZXingScannerView.ResultHandler {
    public static final String BARCODE_CONTENTS = "barcodeContents";
    public static final String BARCODE_FORMAT = "barcodeFormat";

    private ZXingScannerView mScannerView;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        if (rawResult == null) {
            setResult(RESULT_CANCELED);
            finish();
            return;
        }

        Intent i = new Intent();
        i.putExtra(BARCODE_CONTENTS, rawResult.getText());
        i.putExtra(BARCODE_FORMAT, rawResult.getBarcodeFormat().toString());

        setResult(RESULT_OK, i);
        finish();
    }
}

