package hu.ait.soundrecordexample;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "AudioRecorderExample";
    private String mFileName = Environment.getExternalStorageDirectory().getAbsolutePath() +
            "/audiorecordtest.3gp";
    private MediaPlayer mPlayer;
    private MediaRecorder mRecorder;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViews();

        requestNeededPermission();
    }

    public void requestNeededPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    101);
        } else {
            // we are ok
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 101: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 1
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "Permissions granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this,
                            "Permissions are NOT granted", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    private void setViews() {
        Button startRecButton = (Button) findViewById(R.id.startRecordingButton);
        Button stopRecButton = (Button) findViewById(R.id.stopRecordingButton);
        Button startPlayButton = (Button) findViewById(R.id.startPlayingButton);
        Button stopPlayButton = (Button) findViewById(R.id.stopPlayingButton);

        startRecButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startRecording();
            }
        });
        stopRecButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRecording();
            }
        });
        startPlayButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startPlaying();
            }
        });
        stopPlayButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlaying();
            }
        });

    }

    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }

    private void startRecording() {
        try {
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(
                    MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(
                    MediaRecorder.OutputFormat.THREE_GPP);
            File outputFile = new File(mFileName);
            if (outputFile.exists())
                outputFile.delete();
            outputFile.createNewFile();
            mRecorder.setOutputFile(mFileName);

            mRecorder.setAudioEncoder(
                    MediaRecorder.AudioEncoder.AMR_NB);
            mRecorder.prepare();
            mRecorder.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }
}