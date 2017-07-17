package krawa.estafetatest2.ui.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.util.SortedListAdapterCallback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import krawa.estafetatest2.BR;
import krawa.estafetatest2.R;

public abstract class BaseEndlessListAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final SortedList<Item> mList;
    private final BaseListFragmentPresenter mPresenter;
    private boolean hasMore;

    public BaseEndlessListAdapter(BaseListFragmentPresenter presenter) {
        mPresenter = presenter;
        mList = new SortedList<>(Item.class, new SortedListAdapterCallback<Item>(this) {
            @Override
            public int compare(Item item1, Item item2) {
                if(item1.itemType == item2.itemType && item1.itemType == Item.TYPE_ITEM){
                    return BaseEndlessListAdapter.this.compare((T)item1.object, (T)item2.object);
                }
                return item1.itemType - item2.itemType;
            }

            @Override
            public boolean areContentsTheSame(Item oldItem, Item newItem) {
                if(oldItem.itemType == newItem.itemType && oldItem.itemType == Item.TYPE_MORE) return false;
                return oldItem.itemType == newItem.itemType && BaseEndlessListAdapter.this.areContentsTheSame((T)oldItem.object, (T)newItem.object);
            }

            @Override
            public boolean areItemsTheSame(Item item1, Item item2) {
                return item1 != null && item1.equals(item2);
            }

        });

    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).itemType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case Item.TYPE_MORE:
                return new MoreItemHolder(inflate(R.layout.more_item, parent));
            case Item.TYPE_ITEM:
                return new BindingHolder(inflate(getLayoutRes(), parent));
        }
        throw new IllegalArgumentException("Illegal viewType");
    }

    protected View inflate(int layout, ViewGroup parent){
        return LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Item curItem = mList.get(position);
        switch (curItem.itemType){
            case Item.TYPE_ITEM:
                onBindItemHolder(curItem.object, (BindingHolder) holder);
                break;
            case Item.TYPE_MORE:
                onBindMoreItemHolder((MoreItemHolder) holder);
                break;
        }
    }

    protected void onBindItemHolder(Object object, final BindingHolder holder) {
        holder.getBinding().setVariable(BR.item, object);
        holder.getBinding().setVariable(BR.presenter, mPresenter);
    }

    private void onBindMoreItemHolder(MoreItemHolder holder) {}

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public T getItem(int position){
        return (T) mList.get(position).object;
    }

    public void addAll(List<T> newList, boolean hasMore, boolean clear) {
        if(clear) removeAll();
        mList.beginBatchedUpdates();
        if(newList != null){
            for (T obj : newList) {
                addItem(obj);
            }
        }
        setHasMore(hasMore);
        mList.endBatchedUpdates();
    }

    public void addItem(T object) {
        Item item = new Item(Item.TYPE_ITEM);
        item.object = object;
        mList.add(item);
    }

    public void removeItem(T object) {
        for (int i = mList.size() - 1; i >= 0; i--) {
            Item listItem = mList.get(i);
            if(listItem.itemType == Item.TYPE_ITEM
                    && listItem.object.equals(object)){
                mList.removeItemAt(i);
                break;
            }
        }
    }

    public void updateItem(T object) {
        for (int i = 0; i < mList.size(); i++) {
            Item listItem = mList.get(i);
            if(listItem.itemType == Item.TYPE_ITEM && listItem.object.equals(object)){
                listItem.object = object;
                mList.updateItemAt(i, listItem);
                break;
            }
        }
    }

    public void removeAll() {
        mList.beginBatchedUpdates();
        for (int i = mList.size() - 1; i >= 0; i--) {
            Item item = mList.get(i);
            if(item.itemType == Item.TYPE_ITEM){
                mList.removeItemAt(i);
            }
        }
        mList.endBatchedUpdates();
    }

    public void setHasMore(boolean hasMore) {
        if(this.hasMore != hasMore){
            this.hasMore = hasMore;
            if(hasMore){
                mList.add(new Item(Item.TYPE_MORE));
            }else {
                for (int i = mList.size() - 1; i >= 0; i--) {
                    if(mList.get(i).itemType == Item.TYPE_MORE){
                        mList.removeItemAt(i);
                        break;
                    }
                }
            }
        }
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public int getSize() {
        return mList.size() - (hasMore ? 1 : 0);
    }

    protected class Item<M> {

        private static final int TYPE_ITEM = 0;
        private static final int TYPE_MORE = 1;

        int itemType;
        T object;

        private Item(int itemType){
            this.itemType = itemType;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Item item = (Item) o;

            if (itemType != item.itemType) return false;
            return object != null ? object.equals(item.object) : item.object == null;

        }

        @Override
        public int hashCode() {
            int result = itemType;
            result = 31 * result + (object != null ? object.hashCode() : 0);
            return result;
        }
    }

    protected static class BindingHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding binding;
        public BindingHolder(View v) {
            super(v);
            binding = DataBindingUtil.bind(v);
        }
        public ViewDataBinding getBinding() {
            return binding;
        }
    }

    private static class MoreItemHolder extends RecyclerView.ViewHolder {
        public MoreItemHolder(final View itemView) {
            super(itemView);
        }
    }

    protected abstract int compare(T item1, T item2);
    protected abstract boolean areContentsTheSame(T oldItem, T newItem);
    protected abstract int getLayoutRes();

}
