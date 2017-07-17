package krawa.estafetatest2.ui.imagefinder;

import android.support.v7.widget.RecyclerView;

import com.arellomobile.mvp.presenter.InjectPresenter;

import krawa.estafetatest2.model.Image;
import krawa.estafetatest2.ui.base.BaseListFragment;
import krawa.estafetatest2.ui.base.BaseListFragmentPresenter;

public class ImageFinderFragment extends BaseListFragment<Image> implements ImageFinderView{

    public static final String TAG = "ImageFinderFragment";

    @InjectPresenter
    ImageFinderPresenter presenter;

    private ImageFinderAdapter adapter;

    @Override
    protected BaseListFragmentPresenter providePresenter() {
        return presenter;
    }

    @Override
    protected RecyclerView.Adapter provideListAdapter() {
        adapter = new ImageFinderAdapter(presenter);
        return adapter;
    }
}
