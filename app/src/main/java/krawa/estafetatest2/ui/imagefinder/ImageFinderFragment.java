package krawa.estafetatest2.ui.imagefinder;

import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import krawa.estafetatest2.R;
import krawa.estafetatest2.model.Image;
import krawa.estafetatest2.ui.base.BaseListFragment;
import krawa.estafetatest2.ui.base.BaseListFragmentPresenter;

public class ImageFinderFragment extends BaseListFragment<Image> implements ImageFinderView {

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

    @Override
    protected void onSetupList(RecyclerView list) {
        super.onSetupList(list);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.item_divider));
        list.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void updateList(List<Image> items, boolean hasMore, boolean clear) {
        adapter.addAll(items, hasMore, clear);
        super.updateList(items, hasMore, clear);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.finder_menu, menu);
        final MenuItem menuItem = menu.findItem( R.id.action_search);
        final SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onCreateOptionsMenu query=" + query);
                if(!searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                MenuItemCompat.collapseActionView(menuItem);
                presenter.onQueryTextSubmit(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }
}
