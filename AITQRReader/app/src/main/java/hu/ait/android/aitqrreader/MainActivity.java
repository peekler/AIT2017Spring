package hu.ait.android.aitqrreader;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_CAMERA_PERMISSION = 101;
    private TextView barcodeInfo;

    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private CameraSourcePreview preview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        barcodeInfo = (TextView)findViewById(R.id.code_info);
        preview = (CameraSourcePreview) findViewById(R.id.cameraSourcePreview);

        requestNeededPermission();
    }

    public void requestNeededPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {
                Toast.makeText(MainActivity.this, "I need Camera", Toast.LENGTH_SHORT).show();
            }

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_CODE_CAMERA_PERMISSION);
        } else {
            setupBarcodeDetector();
            setupCameraSource();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_CAMERA_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(MainActivity.this, "CAMERA perm granted", Toast.LENGTH_SHORT).show();
                    setupBarcodeDetector();
                    setupCameraSource();
                } else {
                    Toast.makeText(MainActivity.this, "CAMERA perm NOT granted", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        startCameraSource();
    }


    private void startCameraSource() {
        if (cameraSource != null) {
            try {
                preview.start(cameraSource);
            } catch (IOException e) {
                cameraSource.release();
                cameraSource = null;
            }
        }
    }


    private void setupBarcodeDetector() {
        barcodeDetector = new BarcodeDetector.Builder(this)
                //.setBarcodeFormats(Barcode.QR_CODE)
                .build();

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                if (barcodes.size() != 0) {
                    barcodeInfo.post(new Runnable() {    // Use the post method of the TextView
                        public void run() {
                            barcodeInfo.setText(    // Update the TextView
                                    barcodes.valueAt(0).displayValue
                            );
                        }
                    });
                }
            }
        });

        if (!barcodeDetector.isOperational()) {
            Log.w("TAG_QR", "Detector dependencies are not yet available.");
        }

    }

    private void setupCameraSource() {
        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedFps(15.0f)
                .setRequestedPreviewSize(640, 640)
                .build();
    }

    @Override
    protected void onPause() {
        super.onPause();
        preview.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cameraSource != null) {
            cameraSource.release();
        }
    }

}
