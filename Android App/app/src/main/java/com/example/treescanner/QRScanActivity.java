package com.example.treescanner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import eu.livotov.labs.android.camview.ScannerLiveView;
import eu.livotov.labs.android.camview.scanner.decoder.zxing.ZXDecoder;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.VIBRATE;

import com.google.android.material.bottomsheet.BottomSheetDialog;


public class QRScanActivity extends AppCompatActivity {
    private ScannerLiveView camera;
    private TextView scannedTV;

    private TextView dispText;
    private ImageButton showbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscan);

        if (checkPermission()) {
            // if permission is already granted display a toast message
            Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
        } else {

            requestPermission();
        }

        scannedTV = findViewById(R.id.idTVscanned);
        camera = (ScannerLiveView) findViewById(R.id.camview);
        showbtn = findViewById(R.id.QRCaptureButton);

        camera.setScannerViewEventListener(new ScannerLiveView.ScannerViewEventListener() {
            @Override
            public void onScannerStarted(ScannerLiveView scanner) {
                // method is called when scanner is started
                Toast.makeText(QRScanActivity.this, "Scanner Started", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onScannerStopped(ScannerLiveView scanner) {
                // method is called when scanner is stopped.
                Toast.makeText(QRScanActivity.this, "Scanner Stopped", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onScannerError(Throwable err) {
                // method is called when scanner gives some error.
                Toast.makeText(QRScanActivity.this, "Scanner Error: " + err.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeScanned(String data) {
                scannedTV.setText(data);
                showbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(QRScanActivity.this, "Showing Info", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(QRScanActivity.this, QRInfoActivity.class);
                        //TODO-fix split
                        String[] qrdata = data.split("\\[", 2);
                        String code = qrdata[qrdata.length-1];
                        Toast.makeText(QRScanActivity.this, code, Toast.LENGTH_SHORT).show();
                        intent.putExtra("qrdata", code.substring(0, code.length()-1));
                        intent.putExtra("origin", "qrscan");

                        startActivity(intent);
//                        finish();

//                        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
//                                QRScanActivity.this, R.style.BottomSheetDialogTheme
//                        );
//                        View bottomSheetView = LayoutInflater.from(getApplicationContext())
//                                .inflate(
//                                        R.layout.activity_qrinfo,
//                                        (LinearLayout)findViewById(R.id.qrinfosheet)
//                                );
//                        bottomSheetDialog.setContentView(bottomSheetView);
//                        dispText = bottomSheetDialog.findViewById(R.id.displayqrdata);
//                        dispText.setText(data);
//                        bottomSheetDialog.show();
                    }
                });
                // method is called when camera scans the
                // qr code and the data from qr code is
                // stored in data in string format.


            }
        });


        ImageView backbtn = findViewById(R.id.backbutton);
        backbtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(QRScanActivity.this,MainActivity.class));

            finish();
        }

        });


//        showbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
//                        QRScanActivity.this, R.style.BottomSheetDialogTheme
//                );
//                View bottomSheetView = LayoutInflater.from(getApplicationContext())
//                        .inflate(
//                                R.layout.activity_qrinfo,
//                                (FrameLayout)findViewById(R.id.qrinfosheet)
//                        );
//                bottomSheetDialog.setContentView(bottomSheetView);
//                bottomSheetDialog.show();
//            }
//        });


    }
    @Override
    protected void onResume() {
        super.onResume();
        ZXDecoder decoder = new ZXDecoder();
        // 0.5 is the area where we have
        // to place red marker for scanning.
        decoder.setScanAreaPercent(0.8);
        // below method will set decoder to camera.
        camera.setDecoder(decoder);
        camera.startScanner();
    }
    @Override
    protected void onPause() {
        // on app pause the
        // camera will stop scanning.
        camera.stopScanner();
        super.onPause();
    }
    private boolean checkPermission() {
        // here we are checking two permission that is vibrate
        // and camera which is granted by user and not.
        // if permission is granted then we are returning
        // true otherwise false.
        int camera_permission = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int vibrate_permission = ContextCompat.checkSelfPermission(getApplicationContext(), VIBRATE);
        return camera_permission == PackageManager.PERMISSION_GRANTED && vibrate_permission == PackageManager.PERMISSION_GRANTED;
    }


    private void requestPermission() {
        // this method is to request
        // the runtime permission.
        int PERMISSION_REQUEST_CODE = 200;
        ActivityCompat.requestPermissions(this, new String[]{CAMERA, VIBRATE}, PERMISSION_REQUEST_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        // this method is called when user
        // allows the permission to use camera.
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0) {
            boolean cameraaccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            boolean vibrateaccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
            if (cameraaccepted && vibrateaccepted) {
                Toast.makeText(this, "Permission granted..", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Denied \n You cannot use app without providing permission", Toast.LENGTH_SHORT).show();
            }
        }
    }
}