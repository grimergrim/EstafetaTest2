package krawa.estafetatest2.data;

import javax.inject.Inject;

import krawa.estafetatest2.App;
import krawa.estafetatest2.R;
import krawa.estafetatest2.model.SearchResult;
import krawa.estafetatest2.network.GoogleImageAPI;
import krawa.estafetatest2.utils.StringUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkDataSource implements DataSource{

    @Inject
    GoogleImageAPI googleImageAPI;
    @Inject
    StringUtils stringUtils;

    public NetworkDataSource(){
        App.getAppComponent().inject(this);
    }

    @Override
    public void searchImages(String query, final DataListener<SearchResult> listener) {
        googleImageAPI.searchImages(query, GoogleImageAPI.CX,
                "image", "jpg", "medium",
                GoogleImageAPI.API_KEY).enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                if(response.isSuccessful()){
                    listener.onResult(response.body());
                } else {
                    listener.onFailure(stringUtils.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
                listener.onFailure(stringUtils.getString(R.string.connect_error));
            }
        });
    }

}
