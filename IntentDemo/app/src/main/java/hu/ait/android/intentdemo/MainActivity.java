package hu.ait.android.intentdemo;

import android.Manifest;
import android.app.SearchManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText etSearch;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl("http://www.google.com");

        if (getIntent().getData() != null) {
            String file = getIntent().getData().getPath();
            Toast.makeText(this, file, Toast.LENGTH_LONG).show();
        }

        etSearch = (EditText) findViewById(R.id.etSearch);
        TextView tvData = (TextView) findViewById(R.id.tvData);
        Button btnIntent = (Button) findViewById(R.id.btnIntent);
        btnIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intentPhoneCall();
                intentPickContact();
                //intentSearch(etSearch.getText().toString());
                //intentShare(etSearch.getText().toString());
                //intentSendEmail();
            }
        });

        requestNeededPermission();
    }

    public void requestNeededPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CALL_PHONE)) {
                Toast.makeText(MainActivity.this,
                        "I need it for call", Toast.LENGTH_SHORT).show();
            }

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    101);
        } else {
            // already have permission
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
                    Toast.makeText(MainActivity.this, "CALL perm granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this,
                            "CALL perm NOT granted", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }


    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    public void intentPhoneCall() {
        Intent intentCall = new Intent(Intent.ACTION_CALL,
                Uri.parse("tel:+36208225581"));
        try {
            startActivity(intentCall);
        }catch (SecurityException e){
        }
    }

    public void intentPickContact() {
        Intent i = new Intent(Intent.ACTION_PICK,
                ContactsContract.Contacts.CONTENT_URI);
        startActivity(i);
        //startActivityForResult(i,101);
    }

    public void intentSearch(String text) {
        Intent intentSearch = new Intent(Intent.ACTION_WEB_SEARCH);
        intentSearch.putExtra(SearchManager.QUERY, text);
        startActivity(intentSearch);
    }

    public void intentShare(String text) {
        Intent intentTest = new Intent(Intent.ACTION_SEND);
        intentTest.setType("text/plain");
        intentTest.putExtra(Intent.EXTRA_TEXT, text);
        //intentTest.setPackage("com.facebook.katana");
        startActivity(Intent.createChooser(intentTest, "Select share app"));
        //startActivity(intentTest);
    }

    public void intentSendEmail() {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  ,
                new String[]{"peter.ekler@aut.bme.hu"});
        i.putExtra(Intent.EXTRA_SUBJECT, "AIT subject");
        i.putExtra(Intent.EXTRA_TEXT   , "You have a lesson now");
        try {
            startActivity(Intent.createChooser(i,
                    "Send email."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "There are no mail clients.", Toast.LENGTH_SHORT).show();
        }
    }

}
