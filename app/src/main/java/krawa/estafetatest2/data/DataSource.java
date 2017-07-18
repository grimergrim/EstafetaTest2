package krawa.estafetatest2.data;

import krawa.estafetatest2.model.SearchResult;

public interface DataSource {

    void searchImages(String query, DataListener<SearchResult> listener);

}
