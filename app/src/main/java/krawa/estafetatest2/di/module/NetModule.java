package krawa.estafetatest2.di.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import krawa.estafetatest2.Constants;
import krawa.estafetatest2.di.scope.PerApplication;
import krawa.estafetatest2.network.GoogleImageAPI;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetModule {

    private static final String TAG = "NetModule";

    @Provides
    @PerApplication
    GoogleImageAPI provideGoogleImageAPI(Retrofit retrofit){
        return retrofit.create(GoogleImageAPI.class);
    }

    @Provides
    @PerApplication
    Retrofit provideRetrofit(OkHttpClient okHttpClient, Gson gson){
        return new Retrofit.Builder()
                .baseUrl(GoogleImageAPI.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Provides
    @PerApplication
    OkHttpClient provideOkHttpClient(){
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.connectTimeout(30, TimeUnit.SECONDS);
        clientBuilder.readTimeout(30, TimeUnit.SECONDS);
        clientBuilder.writeTimeout(30, TimeUnit.SECONDS);
        clientBuilder.retryOnConnectionFailure(true);
        if(Constants.IS_DEBUG){
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            clientBuilder.addInterceptor(logging);
        }
        return clientBuilder.build();
    }

    @Provides
    @PerApplication
    Gson provideGson() {
        return new GsonBuilder().create();
    }

}
