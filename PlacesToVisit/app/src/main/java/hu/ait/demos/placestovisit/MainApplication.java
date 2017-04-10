package hu.ait.demos.placestovisit;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainApplication  extends Application {

    private Realm realmPlaces;

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
    }

    public void openRealm() {
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        realmPlaces = Realm.getInstance(config);
    }

    public void closeRealm() {
        realmPlaces.close();
    }

    public Realm getRealmPlaces() {
        return realmPlaces;
    }
}
