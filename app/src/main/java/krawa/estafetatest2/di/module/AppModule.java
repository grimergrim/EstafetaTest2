package krawa.estafetatest2.di.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import krawa.estafetatest2.App;
import krawa.estafetatest2.di.scope.PerApplication;

@Module
public class AppModule {

    protected final App application;

    public AppModule(App application) {
        this.application = application;
    }

    @Provides
    @PerApplication
    public Context provideContext() {
        return application.getApplicationContext();
    }

}
