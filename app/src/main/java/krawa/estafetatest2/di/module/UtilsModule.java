package krawa.estafetatest2.di.module;

import android.content.Context;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;
import krawa.estafetatest2.di.scope.PerApplication;
import krawa.estafetatest2.utils.ImageUtils;
import krawa.estafetatest2.utils.LocationUtils;
import krawa.estafetatest2.utils.StringUtils;
import okhttp3.OkHttpClient;

@Module
public class UtilsModule {

    @Provides
    @PerApplication
    public LocationUtils provideLocationUtils(Context context) {
        return new LocationUtils(context);
    }

    @Provides
    @PerApplication
    public ImageUtils provideImageUtils(Context context) {
        return new ImageUtils(context);
    }

    @Provides
    @PerApplication
    public StringUtils provideStringUtils(Context context) {
        return new StringUtils(context);
    }

    @Provides
    @PerApplication
    public Picasso providePicasso(Context context, OkHttpClient okHttpClient) {
        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(okHttpClient));
        return builder.build();
    }

}
