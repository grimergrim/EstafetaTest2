package krawa.estafetatest2.ui.imagefinder;

import com.arellomobile.mvp.InjectViewState;

import krawa.estafetatest2.ui.base.BaseListFragmentPresenter;

@InjectViewState
public class ImageFinderPresenter extends BaseListFragmentPresenter<ImageFinderView> {

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().setEmptyText(null);
    }

    public void onQueryTextSubmit(String query) {
        getViewState().showListProgress(true);
    }

}
