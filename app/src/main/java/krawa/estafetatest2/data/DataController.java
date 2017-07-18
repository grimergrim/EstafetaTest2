package krawa.estafetatest2.data;

import android.location.Location;

import java.util.List;

import javax.inject.Inject;

import krawa.estafetatest2.App;
import krawa.estafetatest2.model.Image;
import krawa.estafetatest2.model.SearchResult;
import krawa.estafetatest2.utils.ImageUtils;
import krawa.estafetatest2.utils.LocationUtils;

public class DataController implements DataSource{

    private static final String TAG = "DataController";

    @Inject
    NetworkDataSource networkDataSource;
    @Inject
    Database database;
    @Inject
    LocationUtils locationUtils;
    @Inject
    ImageUtils imageUtils;

    public DataController(){
        App.getAppComponent().inject(this);
    }

    @Override
    public void searchImages(String query, final DataListener<SearchResult> listener) {
        networkDataSource.searchImages(query, new DataListener<SearchResult>() {
            @Override
            public void onResult(SearchResult result) {
                listener.onResult(result);
                getLocationAndSaveImages(result.getItems());
            }

            @Override
            public void onFailure(String error) {
                listener.onFailure(error);
            }
        });
    }

    private void getLocationAndSaveImages(final List<Image> items) {
        locationUtils.getCurrentLocation(new LocationUtils.OnCurrentLocationCallback() {
            @Override
            public void onCurrentLocationResult(Location location) {
                if(location != null){
                    for (Image item : items) {
                        item.setLat(location.getLatitude());
                        item.setLon(location.getLongitude());
                    }
                }
                saveImages(items);
            }
        });
    }

    private void saveImages(List<Image> images) {
        imageUtils.saveImages(images, new ImageUtils.OnImageSaveCallback() {
            @Override
            public void onImageSaveResult(List<Image> images) {
                if(images != null) {
                    database.saveImages(images);
                }
            }
        });
    }

}
