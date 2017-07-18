package krawa.estafetatest2.ui.imagefinder;

import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;

import krawa.estafetatest2.App;
import krawa.estafetatest2.data.DataController;
import krawa.estafetatest2.data.DataListener;
import krawa.estafetatest2.model.SearchResult;
import krawa.estafetatest2.ui.base.BaseListFragmentPresenter;

@InjectViewState
public class ImageFinderPresenter extends BaseListFragmentPresenter<ImageFinderView> {

    @Inject
    DataController dataController;

    public ImageFinderPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().setEmptyText(null);
    }

    public void onQueryTextSubmit(String query) {
        getViewState().showListProgress(true);
        dataController.searchImages(query, new DataListener<SearchResult>() {
            @Override
            public void onResult(SearchResult result) {
                getViewState().showListProgress(false);
                getViewState().updateList(result.getItems(), false, true);
            }

            @Override
            public void onFailure(String error) {
                getViewState().showListProgress(false);
                getViewState().setEmptyText(error);
                getViewState().showMessage(error);
            }
        });
    }

}
