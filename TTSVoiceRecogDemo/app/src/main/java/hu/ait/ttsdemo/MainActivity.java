package hu.ait.ttsdemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static android.Manifest.*;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    public static final String TAG = "MainActivity";

    private EditText etData;
    private TextView tvDetectedText;
    private TextToSpeech tts;
    private android.speech.SpeechRecognizer sr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tts = new TextToSpeech(this, this);

        etData = (EditText) findViewById(R.id.etData);
        tvDetectedText = (TextView) findViewById(R.id.tvDetectedText);

        Button btnRead = (Button) findViewById(R.id.btnRead);
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak(etData.getText().toString());
            }
        });

        Button btnDetect = (Button) findViewById(R.id.btnDetect);
        btnDetect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,
                        "hu-HU");
                intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                        "demo.android.app.hu.ttsvoicerecogdemo");

                intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
                sr.startListening(intent);
            }
        });


        requestNeededPermission();



    }

    public void requestNeededPermission() {
        if (ContextCompat.checkSelfPermission(this,
                permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    permission.RECORD_AUDIO)) {
                Toast.makeText(MainActivity.this,
                        "I need it for camera", Toast.LENGTH_SHORT).show();
            }

            ActivityCompat.requestPermissions(this,
                    new String[]{permission.RECORD_AUDIO},
                    101);
        } else {
            sr = android.speech.SpeechRecognizer
                    .createSpeechRecognizer(this);
            sr.setRecognitionListener(new SpeechRecognizer());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 101: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(MainActivity.this, "RECORD_AUDIO perm granted", Toast.LENGTH_SHORT).show();

                    sr = android.speech.SpeechRecognizer
                            .createSpeechRecognizer(this);
                    sr.setRecognitionListener(new SpeechRecognizer());

                } else {
                    Toast.makeText(MainActivity.this,
                            "RECORD_AUDIO perm NOT granted", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        try {
            if (tts != null) {
                tts.stop();
                tts.shutdown();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (sr != null) {
                sr.destroy();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            // tts.setSpeechRate((float) 0.8);
            // tts.setPitch(1.0f); tts.setPitch(1.1f);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Intent installIntent = new Intent();
                installIntent
                        .setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installIntent);
            } else {
                speak("Speech system works perfectly!");
            }

        } else {
            Intent installIntent = new Intent();
            installIntent
                    .setAction(
                            TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
            startActivity(installIntent);
        }
    }

    private void speak(String text)
    {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }


    class SpeechRecognizer implements RecognitionListener {
        public void onReadyForSpeech(Bundle params) {
            Log.d(TAG, "onReadyForSpeech");
        }

        public void onBeginningOfSpeech() {
            Log.d(TAG, "onBeginningOfSpeech");
        }

        public void onRmsChanged(float rmsdB) {
            Log.d(TAG, "onRmsChanged");
        }

        public void onBufferReceived(byte[] buffer) {
            Log.d(TAG, "onBufferReceived");
        }

        public void onEndOfSpeech() {
            Log.d(TAG, "onEndofSpeech");
        }

        public void onError(int error) {
            Log.d(TAG, "error " + error);
        }

        public void onResults(Bundle results) {
            String str = new String();
            Log.d(TAG, "onResults " + results);
            ArrayList<String> data = results
                    .getStringArrayList(
                            android.speech.SpeechRecognizer.RESULTS_RECOGNITION);
            tvDetectedText.setText("");
            boolean timeDetected = false;
            boolean sureDetected = false;
            for (String text : data) {
                tvDetectedText.append(text + "\n");
                if (!timeDetected && text.contains("time")) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat(
                            "HH:mm:ss");
                    speak(dateFormat.format(new Date(System.currentTimeMillis())));
                    timeDetected = true;
                }

                if (!sureDetected && text.contains("sure")) {
                    speak("Yes, I'm sure Peter");
                    sureDetected = true;
                }
            }
        }

        public void onPartialResults(Bundle partialResults) {
            Log.d(TAG, "onPartialResults");
        }

        public void onEvent(int eventType, Bundle params) {
            Log.d(TAG, "onEvent " + eventType);
        }
    }
}
