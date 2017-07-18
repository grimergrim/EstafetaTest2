package krawa.estafetatest2.data;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import krawa.estafetatest2.App;
import krawa.estafetatest2.model.Image;

public class Database {

    private static final String TAG = "Database";

    @Inject
    Realm realm;

    public Database(){
        App.getAppComponent().inject(this);
    }

    public void saveImages(final List<Image> images) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(images);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "saveImages onSuccess");
            }
        });
    }

}
