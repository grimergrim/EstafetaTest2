package krawa.estafetatest2.network;

import krawa.estafetatest2.model.SearchResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleImageAPI {

    String BASE_URL = "https://www.googleapis.com/";

    @GET("customsearch/v1")
    Call<SearchResult> searchImages (@Query("q") String query,
                                     @Query("cx") String cx,
                                     @Query("searchType") String searchType,
                                     @Query("filetype") String filetype,
                                     @Query("imgSize") String imgSize,
                                     @Query("key") String key);
}
