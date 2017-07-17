package krawa.estafetatest2.ui.base;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import krawa.estafetatest2.R;
import krawa.estafetatest2.databinding.FragmentListBinding;

public abstract class BaseListFragment<T> extends BaseFragment implements BaseListFragmentView<T> {

    private static final String TAG = "BaseListFragment";

    private FragmentListBinding binding;
    private LinearLayoutManager layoutManager;

    public BaseListFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false);
        onSetupList(binding.list);
        return binding.getRoot();
    }

    protected void onSetupList(RecyclerView list) {
        layoutManager = provideLayoutManager();
        list.setLayoutManager(layoutManager);
        list.setAdapter(provideListAdapter());
    }

    protected LinearLayoutManager provideLayoutManager() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        return layoutManager;
    }

    @Override
    public void showListProgress(final boolean show) {

    }

    @Override
    public void setEmptyText(String text) {
        binding.tvEmptyText.setVisibility(binding.list.getAdapter() == null || binding.list.getAdapter().getItemCount() == getDeltaItemCount() ? View.VISIBLE : View.GONE);
        binding.tvEmptyText.setText(text != null ? text : getString(R.string.empty_list));
    }

    protected int getDeltaItemCount() {
        return 0;
    }

    @Override
    public void updateList(List<T> items, boolean hasMore, boolean clear) {
        setEmptyText(null);
    }

    @Override
    public void addItem(T item){}

    @Override
    public void removeItem(T item){}

    @Override
    public void removeAll() {}

    @Override
    public void updateItem(T item){}

    @Override
    public void setListPosition(int position) {
        ((LinearLayoutManager) binding.list.getLayoutManager()).scrollToPositionWithOffset(position, 0);
    }

    @Override
    public void setListTopPadding(int padding) {
        binding.list.setPadding(binding.list.getPaddingLeft(),
                padding,
                binding.list.getPaddingRight(),
                binding.list.getPaddingBottom());
    }


    public RecyclerView getListView(){
        return binding.list;
    }

    public FragmentListBinding getBinding(){
        return binding;
    }

    protected abstract BaseListFragmentPresenter providePresenter();

    protected abstract RecyclerView.Adapter provideListAdapter();

}
