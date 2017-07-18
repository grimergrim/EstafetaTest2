package krawa.estafetatest2.di.component;

import dagger.Component;
import krawa.estafetatest2.App;
import krawa.estafetatest2.data.DataController;
import krawa.estafetatest2.data.Database;
import krawa.estafetatest2.data.NetworkDataSource;
import krawa.estafetatest2.di.module.AppModule;
import krawa.estafetatest2.di.module.DataModule;
import krawa.estafetatest2.di.module.NetModule;
import krawa.estafetatest2.di.module.UtilsModule;
import krawa.estafetatest2.di.scope.PerApplication;
import krawa.estafetatest2.ui.imagefinder.ImageFinderPresenter;
import krawa.estafetatest2.ui.main.MainActivity;

@Component(modules = {AppModule.class,
        DataModule.class,
        NetModule.class,
        UtilsModule.class})

@PerApplication
public interface AppComponent {

    void inject(App app);

    void inject(MainActivity mainActivity);

    void inject(ImageFinderPresenter imageFinderPresenter);

    void inject(DataController dataController);
    void inject(NetworkDataSource networkDataSource);

    void inject(Database database);
}
