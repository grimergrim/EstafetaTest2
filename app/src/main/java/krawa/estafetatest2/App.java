package krawa.estafetatest2;

import android.app.Application;
import android.content.Context;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import krawa.estafetatest2.di.component.AppComponent;
import krawa.estafetatest2.di.component.DaggerAppComponent;
import krawa.estafetatest2.di.module.AppModule;
import krawa.estafetatest2.di.module.DataModule;
import krawa.estafetatest2.di.module.NetModule;
import krawa.estafetatest2.di.module.UtilsModule;

public class App extends Application {

    private static AppComponent appComponent;
    private static Context appContext;

    @Inject
    Picasso picasso;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = buildComponent();
        appComponent.inject(this);
        Picasso.setSingletonInstance(picasso);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        appContext = base;
    }

    private AppComponent buildComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .dataModule(new DataModule())
                .netModule(new NetModule())
                .utilsModule(new UtilsModule())
                .build();
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    public static Context getAppContext() {
        return appContext;
    }

}
