package krawa.estafetatest2.data;

import javax.inject.Inject;

import krawa.estafetatest2.App;
import krawa.estafetatest2.model.SearchResult;

public class DataController implements DataSource{

    @Inject
    NetworkDataSource networkDataSource;

    public DataController(){
        App.getAppComponent().inject(this);
    }

    @Override
    public void searchImages(String query, DataListener<SearchResult> listener) {
        networkDataSource.searchImages(query, listener);
    }

}
