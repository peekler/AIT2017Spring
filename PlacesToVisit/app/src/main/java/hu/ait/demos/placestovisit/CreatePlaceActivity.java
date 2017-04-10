package hu.ait.demos.placestovisit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Date;
import java.util.UUID;

import hu.ait.demos.placestovisit.data.Place;
import io.realm.Realm;


public class CreatePlaceActivity extends AppCompatActivity {
    public static final String KEY_PLACE = "KEY_PLACE";
    private Spinner spinnerPlaceType;
    private EditText etPlace;
    private EditText etPlaceDesc;
    private Place placeToEdit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_place);

        setupUI();

        if (getIntent().getSerializableExtra(MainActivity.KEY_EDIT) != null) {
            initEdit();
        } else {
            initCreate();
        }
    }

    private void initCreate() {
        getRealm().beginTransaction();
        placeToEdit = getRealm().createObject(Place.class, UUID.randomUUID().toString());
        placeToEdit.setPickUpDate(new Date(System.currentTimeMillis()));
        getRealm().commitTransaction();
    }

    private void initEdit() {
        String placeID = getIntent().getStringExtra(MainActivity.KEY_EDIT);
        placeToEdit = getRealm().where(Place.class)
                .equalTo("placeID", placeID)
                .findFirst();

        etPlace.setText(placeToEdit.getPlaceName());
        etPlaceDesc.setText(placeToEdit.getDescription());
        spinnerPlaceType.setSelection(placeToEdit.getPlaceType().getValue());
    }

    private void setupUI() {
        spinnerPlaceType = (Spinner) findViewById(R.id.spinnerPlaceType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.placetypes_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPlaceType.setAdapter(adapter);

        etPlace = (EditText) findViewById(R.id.etPlaceName);
        etPlaceDesc = (EditText) findViewById(R.id.etPlaceDesc);

        Button btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePlace();
            }
        });
    }

    public Realm getRealm() {
        return ((MainApplication)getApplication()).getRealmPlaces();
    }

    private void savePlace() {
        Intent intentResult = new Intent();

        getRealm().beginTransaction();
        placeToEdit.setPlaceName(etPlace.getText().toString());
        placeToEdit.setDescription(etPlaceDesc.getText().toString());
        placeToEdit.setPlaceType(spinnerPlaceType.getSelectedItemPosition());
        getRealm().commitTransaction();

        intentResult.putExtra(KEY_PLACE, placeToEdit.getPlaceID());
        setResult(RESULT_OK, intentResult);
        finish();
    }
}
