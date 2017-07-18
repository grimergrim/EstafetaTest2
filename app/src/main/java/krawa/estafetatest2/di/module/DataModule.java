package krawa.estafetatest2.di.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import krawa.estafetatest2.data.DataController;
import krawa.estafetatest2.data.Database;
import krawa.estafetatest2.data.NetworkDataSource;
import krawa.estafetatest2.di.scope.PerApplication;

@Module
public class DataModule {

    @Provides
    @PerApplication
    public DataController provideDataController() {
        return new DataController();
    }

    @Provides
    @PerApplication
    public NetworkDataSource provideNetworkDataSource() {
        return new NetworkDataSource();
    }

    @Provides
    @PerApplication
    public Realm provideRealm(Context context) {
        Realm.init(context);
        return Realm.getDefaultInstance();
    }

    @Provides
    @PerApplication
    public Database provideDatabase() {
        return new Database();
    }
}
