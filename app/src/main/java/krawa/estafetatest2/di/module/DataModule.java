package krawa.estafetatest2.di.module;

import dagger.Module;
import dagger.Provides;
import krawa.estafetatest2.data.DataController;
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


}
