package krawa.estafetatest2.di.component;

import dagger.Component;
import krawa.estafetatest2.App;
import krawa.estafetatest2.di.module.AppModule;
import krawa.estafetatest2.di.module.DataModule;
import krawa.estafetatest2.di.module.NetModule;
import krawa.estafetatest2.di.module.UtilsModule;
import krawa.estafetatest2.di.scope.PerApplication;

@Component(modules = {AppModule.class,
        DataModule.class,
        NetModule.class,
        UtilsModule.class})

@PerApplication
public interface AppComponent {

    void inject(App app);

}
